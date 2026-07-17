package com.example.ui.screens

import android.app.Application
import android.speech.tts.TextToSpeech
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.*
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale

data class MemoryCard(
    val id: Int,
    val emoji: String,
    var isFlipped: Boolean = false,
    var isMatched: Boolean = false
)

class MainViewModel(application: Application) : AndroidViewModel(application), TextToSpeech.OnInitListener {

    private val progressDatabase = ProgressDatabase.getDatabase(application)
    private val progressRepository = ProgressRepository(progressDatabase.progressDao())

    // UI state for reactive updates from DB
    val userProgress: StateFlow<UserProgress> = progressRepository.progressFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserProgress()
        )

    // TTS state
    private var tts: TextToSpeech? = null
    private var isTtsReady = false

    // Flying balloons list for popping reward game
    var activeBalloons by mutableStateOf<List<BalloonState>>(emptyList())
        private set

    // Counting Game state
    var countingTargetNum by mutableStateOf(1)
    var countingEmoji by mutableStateOf("🍎")
    var countingOptions by mutableStateOf<List<Int>>(emptyList())
    var countingSuccess by mutableStateOf<Boolean?>(null)

    // Color Game state
    var colorTarget by mutableStateOf(LearningData.colorList.first())
    var colorOptions by mutableStateOf<List<ColorItem>>(emptyList())
    var colorSuccess by mutableStateOf<Boolean?>(null)

    // Fruit Sorting Game state
    var fruitVeggieItem by mutableStateOf(LearningData.fruitList.first())
    var fruitVeggieSuccess by mutableStateOf<Boolean?>(null)

    // Shape Game state
    var shapeTarget by mutableStateOf(LearningData.shapeList.first())
    var shapeOptions by mutableStateOf<List<ShapeItem>>(emptyList())
    var shapeSuccess by mutableStateOf<Boolean?>(null)

    // Memory Game state
    var memoryCards by mutableStateOf<List<MemoryCard>>(emptyList())
    var memoryMatchesCount by mutableStateOf(0)
    var memoryPairsCount by mutableStateOf(2) // 2 for Level 1 (4 cards), 3 for Level 2 (6 cards), 4 for Level 3 (8 cards)
    var memoryLevel by mutableStateOf(1)
    private var firstSelectedCardIndex by mutableStateOf<Int?>(null)
    private var isMemoryProcessing by mutableStateOf(false)

    // Tracing Game state
    var tracingTargetChar by mutableStateOf("A")
    var tracingIsLetter by mutableStateOf(true) // true for A-Z, false for 0-9

    init {
        // Initialize Text To Speech
        tts = TextToSpeech(application, this)
        
        // Initialize daily streak
        viewModelScope.launch {
            progressRepository.updateStreak()
        }

        // Setup first games
        generateNewCountingGame()
        generateNewColorGame()
        generateNewFruitSortingGame()
        generateNewShapeGame()
        setupMemoryGame()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            isTtsReady = true
            updateTtsLanguage()
        }
    }

    private fun updateTtsLanguage() {
        if (isTtsReady) {
            val locale = if (userProgress.value.lang == "hi") Locale("hi", "IN") else Locale.US
            tts?.language = locale
        }
    }

    fun speak(textEn: String, textHi: String) {
        if (isTtsReady) {
            val currentLang = userProgress.value.lang
            val locale = if (currentLang == "hi") Locale("hi", "IN") else Locale.US
            tts?.language = locale
            val textToSpeak = if (currentLang == "hi") textHi else textEn
            tts?.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, "BABY_LEARN_TTS")
        }
    }

    fun speakDirect(text: String) {
        if (isTtsReady) {
            val currentLang = userProgress.value.lang
            val locale = if (currentLang == "hi") Locale("hi", "IN") else Locale.US
            tts?.language = locale
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "BABY_LEARN_TTS")
        }
    }

    fun toggleLanguage() {
        viewModelScope.launch {
            progressRepository.toggleLanguage()
            // Wait brief moment for flow update then set TTS language
            updateTtsLanguage()
            speak("English language selected", "हिंदी भाषा चुनी गई")
        }
    }

    fun toggleDarkMode() {
        viewModelScope.launch {
            progressRepository.toggleDarkMode()
        }
    }

    fun addStars(amount: Int) {
        viewModelScope.launch {
            progressRepository.addStars(amount)
            checkRewardUnlocks()
        }
    }

    private suspend fun checkRewardUnlocks() {
        val progress = progressRepository.getProgress()
        val stars = progress.totalStars

        // Automatic unlock logic based on stars or milestones
        if (stars >= 10) progressRepository.unlockSticker("sticker_abc")
        if (stars >= 20) progressRepository.unlockSticker("sticker_math")
        if (stars >= 30) progressRepository.unlockSticker("sticker_color")
        if (stars >= 40) progressRepository.unlockSticker("sticker_animal")
        if (stars >= 50) progressRepository.unlockSticker("sticker_fruit")
        if (stars >= 60) progressRepository.unlockSticker("sticker_vehicle")
        if (stars >= 75) progressRepository.unlockSticker("sticker_shape")
        if (stars >= 90) progressRepository.unlockSticker("sticker_memory")
        if (stars >= 110) progressRepository.unlockSticker("sticker_tracing")
        
        if (progress.currentStreak >= 3) progressRepository.unlockSticker("sticker_streak_3")
        if (progress.currentStreak >= 7) progressRepository.unlockSticker("sticker_streak_7")
    }

    fun unlockStickerByStars(stickerId: String, cost: Int) {
        viewModelScope.launch {
            val progress = progressRepository.getProgress()
            if (progress.totalStars >= cost) {
                // Deduct stars & unlock
                progressRepository.saveProgress(progress.copy(totalStars = progress.totalStars - cost))
                progressRepository.unlockSticker(stickerId)
                speak("Sticker unlocked! Well done!", "स्टीकर अनलॉक हो गया! बहुत बढ़िया!")
            } else {
                speak("Earn more stars to unlock!", "अनलॉक करने के लिए और सितारे कमाएं!")
            }
        }
    }

    // Balloon Pop game logic
    fun spawnBalloons(count: Int) {
        val currentList = activeBalloons.toMutableList()
        val colors = listOf(0xFFFF4444, 0xFF33B5E5, 0xFF99CC00, 0xFFFFBB33, 0xFFFF8800, 0xFFAA66CC, 0xFFFF88A8)
        for (i in 1..count) {
            val randomX = (10..90).random() / 100f // relative screen width percentage
            val randomColor = colors.random()
            val randomSpeed = (1500..3000).random() // transition time ms
            val size = (60..100).random()
            currentList.add(BalloonState(id = System.nanoTime() + i, xPercent = randomX, color = randomColor, durationMs = randomSpeed, sizeDp = size))
        }
        activeBalloons = currentList
    }

    fun popBalloon(id: Long) {
        activeBalloons = activeBalloons.filter { it.id != id }
        addStars(1)
        speak("Pop!", "पॉप!")
    }

    fun clearAllBalloons() {
        activeBalloons = emptyList()
    }

    // Game Generator: Counting
    fun generateNewCountingGame() {
        countingSuccess = null
        countingTargetNum = (1..10).random()
        val currentItem = LearningData.abcList.random()
        countingEmoji = currentItem.emoji

        val correctOption = countingTargetNum
        val fakeOption1 = if (correctOption + 1 <= 10) correctOption + 1 else correctOption - 1
        val fakeOption2 = if (correctOption - 2 >= 1) correctOption - 2 else correctOption + 2

        countingOptions = listOf(correctOption, fakeOption1, fakeOption2).distinct().shuffled()
    }

    fun submitCountingAnswer(answer: Int) {
        if (answer == countingTargetNum) {
            countingSuccess = true
            addStars(5)
            spawnBalloons(5)
            speak("Correct! Five stars for you!", "सही जवाब! आपके लिए पांच सितारे!")
            viewModelScope.launch {
                progressRepository.markModuleCompleted("numbers")
            }
        } else {
            countingSuccess = false
            speak("Try again, buddy!", "फिर से कोशिश करो दोस्त!")
        }
    }

    // Game Generator: Colors
    fun generateNewColorGame() {
        colorSuccess = null
        colorTarget = LearningData.colorList.random()
        colorOptions = LearningData.colorList.shuffled().take(3)
        if (!colorOptions.contains(colorTarget)) {
            val mutable = colorOptions.toMutableList()
            mutable[0] = colorTarget
            colorOptions = mutable.shuffled()
        }
    }

    fun submitColorAnswer(selected: ColorItem) {
        if (selected.id == colorTarget.id) {
            colorSuccess = true
            addStars(5)
            spawnBalloons(5)
            speak("Superb! Correct color!", "शानदार! सही रंग!")
            viewModelScope.launch {
                progressRepository.markModuleCompleted("colors")
            }
        } else {
            colorSuccess = false
            speak("Oops, look closer!", "ओह, थोड़ा ध्यान से देखो!")
        }
    }

    // Game Generator: Fruit & Vegetable Sorting
    fun generateNewFruitSortingGame() {
        fruitVeggieSuccess = null
        fruitVeggieItem = LearningData.fruitList.random()
    }

    fun submitFruitSortingAnswer(isFruitBasket: Boolean) {
        val isCorrect = fruitVeggieItem.isFruit == isFruitBasket
        if (isCorrect) {
            fruitVeggieSuccess = true
            addStars(5)
            spawnBalloons(5)
            val name = if (userProgress.value.lang == "hi") fruitVeggieItem.nameHi else fruitVeggieItem.nameEn
            if (isFruitBasket) {
                speak("$name is a delicious fruit!", "$name एक स्वादिष्ट फल है!")
            } else {
                speak("$name is a healthy vegetable!", "$name एक स्वास्थ्यवर्धक सब्जी है!")
            }
            viewModelScope.launch {
                progressRepository.markModuleCompleted("fruits")
            }
        } else {
            fruitVeggieSuccess = false
            speak("Check again!", "फिर से जांचें!")
        }
    }

    // Game Generator: Shapes
    fun generateNewShapeGame() {
        shapeSuccess = null
        shapeTarget = LearningData.shapeList.random()
        shapeOptions = LearningData.shapeList.shuffled().take(3)
        if (!shapeOptions.contains(shapeTarget)) {
            val mutable = shapeOptions.toMutableList()
            mutable[0] = shapeTarget
            shapeOptions = mutable.shuffled()
        }
    }

    fun submitShapeAnswer(selected: ShapeItem) {
        if (selected.id == shapeTarget.id) {
            shapeSuccess = true
            addStars(5)
            spawnBalloons(5)
            speak("Awesome! Correct shape!", "बहुत बढ़िया! सही आकार!")
            viewModelScope.launch {
                progressRepository.markModuleCompleted("shapes")
            }
        } else {
            shapeSuccess = false
            speak("No, try another shape!", "नहीं, कोई दूसरा आकार आजमाएं!")
        }
    }

    // Memory Game Setup & Play
    fun setupMemoryGame() {
        memoryMatchesCount = 0
        firstSelectedCardIndex = null
        isMemoryProcessing = false

        // Determine pairs count based on level
        memoryPairsCount = when (memoryLevel) {
            1 -> 2 // 2 pairs (4 cards)
            2 -> 3 // 3 pairs (6 cards)
            3 -> 4 // 4 pairs (8 cards)
            else -> 6 // 6 pairs (12 cards)
        }

        val availableEmojis = (LearningData.abcList.map { it.emoji } + 
                LearningData.animalList.map { it.emoji } + 
                LearningData.fruitList.map { it.emoji }).distinct().shuffled()

        val selectedEmojis = availableEmojis.take(memoryPairsCount)
        val gameEmojis = (selectedEmojis + selectedEmojis).shuffled()

        memoryCards = gameEmojis.mapIndexed { index, emoji ->
            MemoryCard(id = index, emoji = emoji)
        }
    }

    fun selectMemoryCard(index: Int) {
        if (isMemoryProcessing) return
        val cards = memoryCards.map { it.copy() }

        // Ignore if already flipped or matched
        if (cards[index].isFlipped || cards[index].isMatched) return

        cards[index].isFlipped = true
        memoryCards = cards

        val firstIndex = firstSelectedCardIndex
        if (firstIndex == null) {
            firstSelectedCardIndex = index
            val selectedEmoji = cards[index].emoji
            speakDirect(selectedEmoji)
        } else {
            if (firstIndex == index) return
            isMemoryProcessing = true
            val firstCard = cards[firstIndex]
            val secondCard = cards[index]

            if (firstCard.emoji == secondCard.emoji) {
                // Match!
                viewModelScope.launch {
                    kotlinx.coroutines.delay(500)
                    val updated = memoryCards.mapIndexed { i, card ->
                        if (i == firstIndex || i == index) {
                            card.copy(isMatched = true, isFlipped = true)
                        } else card
                    }
                    memoryCards = updated
                    memoryMatchesCount++
                    addStars(5)
                    spawnBalloons(3)
                    speak("Matched!", "मिला दिया!")

                    firstSelectedCardIndex = null
                    isMemoryProcessing = false

                    if (memoryMatchesCount == memoryPairsCount) {
                        // All matches found, advance level!
                        kotlinx.coroutines.delay(1000)
                        speak("Fantastic! You completed the Memory Puzzle!", "अद्भुत! आपने मेमोरी पहेली पूरी कर ली!")
                        progressRepository.markModuleCompleted("memory")
                        if (memoryLevel < 4) {
                            memoryLevel++
                        } else {
                            memoryLevel = 1
                        }
                        setupMemoryGame()
                    }
                }
            } else {
                // Mismatch
                viewModelScope.launch {
                    kotlinx.coroutines.delay(1000)
                    val updated = memoryCards.mapIndexed { i, card ->
                        if (i == firstIndex || i == index) {
                            card.copy(isFlipped = false)
                        } else card
                    }
                    memoryCards = updated
                    firstSelectedCardIndex = null
                    isMemoryProcessing = false
                }
            }
        }
    }

    fun changeTracingChar(char: String, isLetter: Boolean) {
        tracingTargetChar = char
        tracingIsLetter = isLetter
    }

    fun markTracingCompleted() {
        viewModelScope.launch {
            progressRepository.markModuleCompleted("tracing")
        }
    }

    override fun onCleared() {
        super.onCleared()
        tts?.shutdown()
    }
}

data class BalloonState(
    val id: Long,
    val xPercent: Float,
    val color: Long,
    val durationMs: Int,
    val sizeDp: Int
)
