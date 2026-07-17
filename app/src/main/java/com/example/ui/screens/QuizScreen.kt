package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.LearningData

data class QuizQuestion(
    val category: String,
    val questionTextEn: String,
    val questionTextHi: String,
    val targetNameEn: String,
    val targetNameHi: String,
    val targetEmoji: String,
    val options: List<QuizOption>
)

data class QuizOption(
    val emoji: String,
    val nameEn: String,
    val nameHi: String,
    val isCorrect: Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    viewModel: MainViewModel,
    lang: String,
    onBack: () -> Unit
) {
    val isHindi = lang == "hi"
    var currentQuestion by remember { mutableStateOf<QuizQuestion?>(null) }
    var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }
    var showExplanation by remember { mutableStateOf(false) }
    var isCorrectAnswer by remember { mutableStateOf(false) }

    // Generate a beautiful new quiz question dynamically!
    fun generateQuestion() {
        selectedOptionIndex = null
        showExplanation = false
        isCorrectAnswer = false

        val categories = listOf("animal", "fruit", "vehicle", "shape", "bird")
        val chosenCategory = categories.random()

        when (chosenCategory) {
            "animal" -> {
                val targets = LearningData.animalList.shuffled()
                val target = targets.first()
                val distractors = targets.drop(1).take(3)
                val options = (distractors.map { QuizOption(it.emoji, it.nameEn, it.nameHi, false) } +
                        QuizOption(target.emoji, target.nameEn, target.nameHi, true)).shuffled()

                currentQuestion = QuizQuestion(
                    category = chosenCategory,
                    questionTextEn = "Which one is the ${target.nameEn}? 🦁",
                    questionTextHi = "${target.nameHi} कौन सा है? 🦁",
                    targetNameEn = target.nameEn,
                    targetNameHi = target.nameHi,
                    targetEmoji = target.emoji,
                    options = options
                )
            }
            "fruit" -> {
                val targets = LearningData.fruitList.shuffled()
                val target = targets.first()
                val distractors = targets.drop(1).take(3)
                val options = (distractors.map { QuizOption(it.emoji, it.nameEn, it.nameHi, false) } +
                        QuizOption(target.emoji, target.nameEn, target.nameHi, true)).shuffled()

                currentQuestion = QuizQuestion(
                    category = chosenCategory,
                    questionTextEn = "Which one is the ${target.nameEn}? 🍎",
                    questionTextHi = "${target.nameHi} कौन सा है? 🍎",
                    targetNameEn = target.nameEn,
                    targetNameHi = target.nameHi,
                    targetEmoji = target.emoji,
                    options = options
                )
            }
            "vehicle" -> {
                val targets = LearningData.vehicleList.shuffled()
                val target = targets.first()
                val distractors = targets.drop(1).take(3)
                val options = (distractors.map { QuizOption(it.emoji, it.nameEn, it.nameHi, false) } +
                        QuizOption(target.emoji, target.nameEn, target.nameHi, true)).shuffled()

                currentQuestion = QuizQuestion(
                    category = chosenCategory,
                    questionTextEn = "Which one is the ${target.nameEn}? 🚂",
                    questionTextHi = "${target.nameHi} कौन सा है? 🚂",
                    targetNameEn = target.nameEn,
                    targetNameHi = target.nameHi,
                    targetEmoji = target.emoji,
                    options = options
                )
            }
            "shape" -> {
                val targets = LearningData.shapeList.shuffled()
                val target = targets.first()
                val distractors = targets.drop(1).take(3)
                val options = (distractors.map { QuizOption(it.emoji, target.nameEn, target.nameHi, false) } + // Just placeholder text mapping
                        QuizOption(target.emoji, target.nameEn, target.nameHi, true)).shuffled()

                currentQuestion = QuizQuestion(
                    category = chosenCategory,
                    questionTextEn = "Which one is the ${target.nameEn}? 🔺",
                    questionTextHi = "${target.nameHi} कौन सा है? 🔺",
                    targetNameEn = target.nameEn,
                    targetNameHi = target.nameHi,
                    targetEmoji = target.emoji,
                    options = options
                )
            }
            "bird" -> {
                val targets = LearningData.birdList.shuffled()
                val target = targets.first()
                val distractors = targets.drop(1).take(3)
                val options = (distractors.map { QuizOption(it.emoji, it.nameEn, it.nameHi, false) } +
                        QuizOption(target.emoji, target.nameEn, target.nameHi, true)).shuffled()

                currentQuestion = QuizQuestion(
                    category = chosenCategory,
                    questionTextEn = "Which one is the ${target.nameEn}? 🦜",
                    questionTextHi = "${target.nameHi} कौन सा है? 🦜",
                    targetNameEn = target.nameEn,
                    targetNameHi = target.nameHi,
                    targetEmoji = target.emoji,
                    options = options
                )
            }
        }

        // Trigger speech for the question automatically
        currentQuestion?.let {
            viewModel.speak(it.questionTextEn, it.questionTextHi)
        }
    }

    // Initialize first question
    LaunchedEffect(Unit) {
        generateQuestion()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isHindi) "बच्चों की प्रश्नोत्तरी ❓" else "Kidz Quiz ❓",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("btn_quiz_back")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        PlaygroundBackground {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                currentQuestion?.let { question ->
                    // Star count banner
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFECEFF1))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(imageVector = Icons.Default.Help, contentDescription = "Quiz Question", tint = Color(0xFF607D8B), modifier = Modifier.size(24.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isHindi) "सवाल को ध्यान से सुनें और सही उत्तर चुनें!" else "Listen closely & choose the correct option!",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF37474F),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    // Question Card Section
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9C4)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = if (isHindi) question.questionTextHi else question.questionTextEn,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFFF57F17),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.clickable {
                                    viewModel.speak(question.questionTextEn, question.questionTextHi)
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 4 Options Grid (2x2)
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        items(question.options.size) { idx ->
                            val option = question.options[idx]
                            val isSelected = selectedOptionIndex == idx

                            // Highlight correct answer if user guessed wrong
                            val showCorrectHighlight = showExplanation && option.isCorrect

                            val borderColor = when {
                                isSelected && option.isCorrect -> Color(0xFF4CAF50)
                                isSelected && !option.isCorrect -> Color(0xFFF44336)
                                showCorrectHighlight -> Color(0xFF4CAF50)
                                else -> Color.Transparent
                            }

                            val borderStrokeWidth = if (borderColor != Color.Transparent) 6.dp else 0.dp

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(140.dp)
                                    .clip(RoundedCornerShape(24.dp))
                                    .border(borderStrokeWidth, borderColor, RoundedCornerShape(24.dp))
                                    .clickable(enabled = !showExplanation) {
                                        selectedOptionIndex = idx
                                        showExplanation = true
                                        isCorrectAnswer = option.isCorrect

                                        if (option.isCorrect) {
                                            viewModel.addStars(10)
                                            viewModel.spawnBalloons(10)
                                            viewModel.speak(
                                                "Wow! You are brilliant! Ten stars for you!",
                                                "वाह! तुम बहुत होशियार हो! तुम्हारे लिए दस सितारे!"
                                            )
                                        } else {
                                            viewModel.speak(
                                                "Oops! That is a ${option.nameEn}. The correct answer is ${question.targetNameEn}! Try the next one!",
                                                "ओह! वह ${option.nameHi} है। सही उत्तर ${question.targetNameHi} है! अगला सवाल आज़माएँ!"
                                            )
                                        }
                                    }
                                    .testTag("quiz_option_$idx"),
                                shape = RoundedCornerShape(24.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(text = option.emoji, fontSize = 56.sp)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = if (isHindi) option.nameHi else option.nameEn,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF37474F)
                                    )
                                }
                            }
                        }
                    }

                    // Bottom feedback and NEXT question trigger
                    if (showExplanation) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isCorrectAnswer) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    if (isCorrectAnswer) {
                                        Icon(imageVector = Icons.Default.Celebration, contentDescription = "Yay", tint = Color(0xFF2E7D32), modifier = Modifier.size(28.dp))
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = if (isHindi) "शानदार! सही उत्तर! 🏆" else "Superb! Correct! 🏆",
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Black,
                                            color = Color(0xFF2E7D32)
                                        )
                                    } else {
                                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Correct Highlighted", tint = Color(0xFFC62828), modifier = Modifier.size(28.dp))
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = if (isHindi) "सही उत्तर को हरे रंग में हाइलाइट किया गया है!" else "Correct option is highlighted in Green!",
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Black,
                                            color = Color(0xFFC62828)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                Button(
                                    onClick = { generateQuestion() },
                                    colors = ButtonDefaults.buttonColors(containerColor = if (isCorrectAnswer) Color(0xFF4CAF50) else Color(0xFFE53935)),
                                    shape = RoundedCornerShape(16.dp),
                                    modifier = Modifier
                                        .fillMaxWidth(0.8f)
                                        .testTag("btn_quiz_next")
                                ) {
                                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "Next")
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = if (isHindi) "अगला सवाल" else "Next Question",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
