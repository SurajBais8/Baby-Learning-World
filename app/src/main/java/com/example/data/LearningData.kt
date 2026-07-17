package com.example.data

data class AbcItem(
    val letter: String,
    val nameEn: String,
    val nameHi: String,
    val emoji: String
)

data class NumberItem(
    val number: Int,
    val wordEn: String,
    val wordHi: String,
    val emoji: String
)

data class ColorItem(
    val id: String,
    val nameEn: String,
    val nameHi: String,
    val colorHex: String,
    val emojiRepresentation: String,
    val exampleObjects: List<String> // Emojis of Red objects, etc.
)

data class AnimalItem(
    val id: String,
    val nameEn: String,
    val nameHi: String,
    val soundEn: String,
    val soundHi: String,
    val emoji: String
)

data class FruitItem(
    val id: String,
    val nameEn: String,
    val nameHi: String,
    val emoji: String,
    val isFruit: Boolean // true for fruit, false for vegetable
)

data class BirdItem(
    val id: String,
    val nameEn: String,
    val nameHi: String,
    val soundEn: String,
    val soundHi: String,
    val emoji: String
)

data class VegetableItem(
    val id: String,
    val nameEn: String,
    val nameHi: String,
    val emoji: String
)

data class BodyPartItem(
    val id: String,
    val nameEn: String,
    val nameHi: String,
    val emoji: String
)

data class VehicleItem(
    val id: String,
    val nameEn: String,
    val nameHi: String,
    val soundTextEn: String,
    val soundTextHi: String,
    val emoji: String
)

data class ShapeItem(
    val id: String,
    val nameEn: String,
    val nameHi: String,
    val emoji: String,
    val drawingType: String // "circle", "square", "triangle", "star", "heart", "oval"
)

data class RewardSticker(
    val id: String,
    val nameEn: String,
    val nameHi: String,
    val emoji: String,
    val descriptionEn: String,
    val descriptionHi: String,
    val starCost: Int
)

data class HabitItem(
    val id: String,
    val nameEn: String,
    val nameHi: String,
    val emoji: String,
    val descEn: String,
    val descHi: String
)

data class OppositeItem(
    val id: String,
    val word1En: String,
    val word1Hi: String,
    val word2En: String,
    val word2Hi: String,
    val emoji1: String,
    val emoji2: String
)

data class PlanetItem(
    val id: String,
    val nameEn: String,
    val nameHi: String,
    val emoji: String,
    val colorHex: String,
    val funFactEn: String,
    val funFactHi: String
)

data class InstrumentItem(
    val id: String,
    val nameEn: String,
    val nameHi: String,
    val emoji: String,
    val soundToneHz: Int
)

object LearningData {
    val abcList = listOf(
        AbcItem("A", "Apple", "सेब", "🍎"),
        AbcItem("B", "Ball", "गेंद", "⚽"),
        AbcItem("C", "Cat", "बिल्ली", "🐱"),
        AbcItem("D", "Dog", "कुत्ता", "🐶"),
        AbcItem("E", "Elephant", "हाथी", "🐘"),
        AbcItem("F", "Fish", "मछली", "🐟"),
        AbcItem("G", "Grapes", "अंगूर", "🍇"),
        AbcItem("H", "Horse", "घोड़ा", "🐴"),
        AbcItem("I", "Ice Cream", "आइसक्रीम", "🍦"),
        AbcItem("J", "Jug", "जग", "🥛"),
        AbcItem("K", "Kite", "पतंग", "🪁"),
        AbcItem("L", "Lion", "शेर", "🦁"),
        AbcItem("M", "Monkey", "बंदर", "🐵"),
        AbcItem("N", "Nest", "घोंसला", "🪹"),
        AbcItem("O", "Orange", "संतरा", "🍊"),
        AbcItem("P", "Parrot", "तोता", "🦜"),
        AbcItem("Q", "Queen", "रानी", "👑"),
        AbcItem("R", "Rabbit", "खरगोश", "🐇"),
        AbcItem("S", "Sun", "सूरज", "☀️"),
        AbcItem("T", "Train", "रेलगाड़ी", "🚆"),
        AbcItem("U", "Umbrella", "छाता", "☂️"),
        AbcItem("V", "Van", "गाड़ी", "🚐"),
        AbcItem("W", "Watch", "घड़ी", "⌚"),
        AbcItem("X", "Xylophone", "जलतरंग", "🪘"),
        AbcItem("Y", "Yak", "याक", "🐂"),
        AbcItem("Z", "Zebra", "जेब्रा", "🦓")
    )

    val colorList = listOf(
        ColorItem("red", "Red", "लाल", "0xFFFF4444", "🔴", listOf("🍎", "🍓", "🍅")),
        ColorItem("blue", "Blue", "नीला", "0xFF33B5E5", "🔵", listOf("🫐", "🐋", "🐳")),
        ColorItem("green", "Green", "हरा", "0xFF99CC00", "🟢", listOf("🥦", "🐸", "🍐")),
        ColorItem("yellow", "Yellow", "पीला", "0xFFFFBB33", "🟡", listOf("🍌", "🍋", "🌽")),
        ColorItem("orange", "Orange", "नारंगी", "0xFFFF8800", "🟠", listOf("🍊", "🥕", "🎃")),
        ColorItem("purple", "Purple", "बैंगनी", "0xFFAA66CC", "🟣", listOf("🍇", "🍆", "🪻")),
        ColorItem("pink", "Pink", "गुलाबी", "0xFFFF88A8", "🌸", listOf("🦩", "🍦", "🌸"))
    )

    val animalList = listOf(
        AnimalItem("dog", "Dog", "कुत्ता", "Woof Woof", "भौ भौ", "🐶"),
        AnimalItem("cat", "Cat", "बिल्ली", "Meow Meow", "म्याऊँ म्याऊँ", "🐱"),
        AnimalItem("lion", "Lion", "शेर", "Roar Roar", "दहाड़ दहाड़", "🦁"),
        AnimalItem("elephant", "Elephant", "हाथी", "Trumpet", "चिंघाड़", "🐘"),
        AnimalItem("monkey", "Monkey", "बंदर", "Whoop Whoop", "खी खी", "🐵"),
        AnimalItem("cow", "Cow", "गाय", "Moo Moo", "म्हाँ म्हाँ", "🐮"),
        AnimalItem("sheep", "Sheep", "भेड़", "Baa Baa", "में में", "🐑"),
        AnimalItem("frog", "Frog", "मेंढक", "Ribbit Ribbit", "टर टर", "🐸"),
        AnimalItem("duck", "Duck", "बतख", "Quack Quack", "क्वैक क्वैक", "🦆"),
        AnimalItem("pig", "Pig", "सूअर", "Oink Oink", "घुर घुर", "🐷")
    )

    val fruitList = listOf(
        FruitItem("apple", "Apple", "सेब", "🍎", true),
        FruitItem("banana", "Banana", "केला", "🍌", true),
        FruitItem("mango", "Mango", "आम", "🥭", true),
        FruitItem("strawberry", "Strawberry", "स्ट्रॉबेरी", "🍓", true),
        FruitItem("watermelon", "Watermelon", "तरबूज", "🍉", true),
        FruitItem("grapes", "Grapes", "अंगूर", "🍇", true),
        FruitItem("orange", "Orange", "संतरा", "🍊", true),
        FruitItem("pineapple", "Pineapple", "अनानास", "🍍", true),
        FruitItem("peach", "Peach", "आड़ू", "🍑", true),
        FruitItem("cherry", "Cherry", "चेरी", "🍒", true),
        FruitItem("pear", "Pear", "नाशपाती", "🍐", true),
        FruitItem("kiwi", "Kiwi", "कीवी", "🥝", true),
        FruitItem("pomegranate", "Pomegranate", "अनार", "🍎", true),
        FruitItem("lemon", "Lemon", "नींबू", "🍋", true),
        FruitItem("melon", "Melon", "खरबूजा", "🍈", true),
        FruitItem("blueberry", "Blueberry", "नीलबदरी", "🫐", true),
        FruitItem("raspberry", "Raspberry", "रसभरी", "🍒", true),
        FruitItem("blackberry", "Blackberry", "काली रसभरी", "🫐", true),
        FruitItem("coconut", "Coconut", "नारियल", "🥥", true),
        FruitItem("papaya", "Papaya", "पपीता", "🥭", true),
        FruitItem("fig", "Fig", "अंजीर", "🫐", true),
        FruitItem("plum", "Plum", "आलूबुखारा", "🍑", true),
        FruitItem("apricot", "Apricot", "खुबानी", "🍑", true),
        FruitItem("grapefruit", "Grapefruit", "चकोतरा", "🍊", true),
        FruitItem("lychee", "Lychee", "लीची", "🍒", true)
    )

    val vegetableList = listOf(
        VegetableItem("carrot", "Carrot", "गाजर", "🥕"),
        VegetableItem("broccoli", "Broccoli", "ब्रोकोली", "🥦"),
        VegetableItem("potato", "Potato", "आलू", "🥔"),
        VegetableItem("tomato", "Tomato", "टमाटर", "🍅"),
        VegetableItem("corn", "Corn", "मक्का", "🌽"),
        VegetableItem("eggplant", "Eggplant", "बैंगन", "🍆"),
        VegetableItem("pepper", "Pepper", "शिमला मिर्च", "🫑"),
        VegetableItem("cucumber", "Cucumber", "खीरा", "🥒"),
        VegetableItem("lettuce", "Lettuce", "सलाद पत्ता", "🥬"),
        VegetableItem("onion", "Onion", "प्याज़", "🧅"),
        VegetableItem("garlic", "Garlic", "लहसुन", "🧄"),
        VegetableItem("mushroom", "Mushroom", "मशरूम", "🍄"),
        VegetableItem("sweetpotato", "Sweet Potato", "शकरकंद", "🍠"),
        VegetableItem("pumpkin", "Pumpkin", "कद्दू", "🎃"),
        VegetableItem("avocado", "Avocado", "एवोकैडो", "🥑"),
        VegetableItem("peas", "Peas", "मटर", "🫛"),
        VegetableItem("radish", "Radish", "मूली", "🥬"),
        VegetableItem("cabbage", "Cabbage", "पत्ता गोभी", "🥬"),
        VegetableItem("spinach", "Spinach", "पालक", "🌱"),
        VegetableItem("ginger", "Ginger", "अदरक", "🫚"),
        VegetableItem("chili", "Chili", "मिर्च", "🌶️"),
        VegetableItem("beetroot", "Beetroot", "चुकंदर", "🧅"),
        VegetableItem("celery", "Celery", "अजवाइन", "🌿"),
        VegetableItem("zucchini", "Zucchini", "तुरई", "🥒"),
        VegetableItem("asparagus", "Asparagus", "शतावरी", "🎋")
    )

    val birdList = listOf(
        BirdItem("parrot", "Parrot", "तोता", "Squawk Squawk", "टें टें", "🦜"),
        BirdItem("crow", "Crow", "कौआ", "Caw Caw", "कांव कांव", "🐦"),
        BirdItem("peacock", "Peacock", "मोर", "Scream Scream", "मयूर ध्वनि", "🦚"),
        BirdItem("eagle", "Eagle", "चील", "Screech Screech", "चीख चीख", "🦅"),
        BirdItem("sparrow", "Sparrow", "गौरैया", "Chirp Chirp", "चीं चीं", "🐤"),
        BirdItem("owl", "Owl", "उल्लू", "Hoot Hoot", "घू घू", "🦉"),
        BirdItem("duck", "Duck", "बतख", "Quack Quack", "क्वैक क्वैक", "🦆"),
        BirdItem("hen", "Hen", "मुर्गी", "Cluck Cluck", "कुक्ड़ू कूँ", "🐔"),
        BirdItem("pigeon", "Pigeon", "कबूतर", "Coo Coo", "गुटर गूँ", "🕊️"),
        BirdItem("swan", "Swan", "हंस", "Honk Honk", "हंस ध्वनि", "🦢")
    )

    val bodyPartsList = listOf(
        BodyPartItem("head", "Head", "सिर", "🙆"),
        BodyPartItem("eyes", "Eyes", "आंखें", "👀"),
        BodyPartItem("ears", "Ears", "कान", "👂"),
        BodyPartItem("nose", "Nose", "नाक", "👃"),
        BodyPartItem("mouth", "Mouth", "मुंह", "👄"),
        BodyPartItem("hand", "Hand", "हाथ", "🖐️"),
        BodyPartItem("foot", "Foot", "पैर", "🦶"),
        BodyPartItem("hair", "Hair", "बाल", "💇"),
        BodyPartItem("teeth", "Teeth", "दांत", "🦷"),
        BodyPartItem("tongue", "Tongue", "जीभ", "👅")
    )

    val vehicleList = listOf(
        VehicleItem("car", "Car", "कार", "Vroom Vroom", "ब्रूम ब्रूम", "🚗"),
        VehicleItem("train", "Train", "रेलगाड़ी", "Choo Choo", "छुक छुक", "🚂"),
        VehicleItem("plane", "Plane", "हवाई जहाज", "Whoosh", "शू", "✈️"),
        VehicleItem("bus", "Bus", "बस", "Honk Honk", "पों पों", "🚌"),
        VehicleItem("police", "Police Car", "पुलिस गाड़ी", "Wee Woo Wee Woo", "वी वू वी वू", "🚓"),
        VehicleItem("rocket", "Rocket", "रॉकेट", "Blast off Zoom", "ज़ूम", "🚀"),
        VehicleItem("boat", "Boat", "नाव", "Splash Splash", "छप छप", "boat_emoji"), // We can use ⛵
        VehicleItem("tractor", "Tractor", "ट्रैक्टर", "Chug Chug Chug", "टुक टुक टुक", "🚜")
    ).map { if (it.id == "boat") it.copy(emoji = "⛵") else it }

    val shapeList = listOf(
        ShapeItem("circle", "Circle", "गोला", "🔴", "circle"),
        ShapeItem("square", "Square", "वर्ग", "🟥", "square"),
        ShapeItem("triangle", "Triangle", "त्रिकोण", "🔺", "triangle"),
        ShapeItem("star", "Star", "तारा", "⭐", "star"),
        ShapeItem("heart", "Heart", "दिल", "💖", "heart"),
        ShapeItem("oval", "Oval", "अंडाकार", "🥚", "oval")
    )

    val rewardStickers = listOf(
        RewardSticker("sticker_welcome", "Welcome Buddy", "स्वागत दोस्त", "👶", "Started the adventure!", "साहसिक कार्य शुरू किया!", 0),
        RewardSticker("sticker_abc", "ABC Master", "एबीसी गुरु", "🔤", "Completed ABC session!", "एबीसी सत्र पूरा किया!", 10),
        RewardSticker("sticker_math", "Math Wizard", "गणित जादूगर", "🔢", "Answered 10 counting questions!", "१० गिनती प्रश्नों के उत्तर दिए!", 15),
        RewardSticker("sticker_color", "Color Artist", "रंग कलाकार", "🎨", "Matched all colors correctly!", "सभी रंगों का सही मिलान किया!", 12),
        RewardSticker("sticker_animal", "Animal Buddy", "पशु मित्र", "🦁", "Learned animal sounds!", "जानवरों की आवाजें सीखीं!", 15),
        RewardSticker("sticker_fruit", "Healthy Kid", "स्वस्थ बच्चा", "🍎", "Sorted fruits and vegetables!", "फल और सब्जियां अलग कीं!", 10),
        RewardSticker("sticker_vehicle", "Super Driver", "सुपर驱动", "🚗", "Tapped all vehicle sounds!", "सभी गाड़ियों की आवाजें सुनीं!", 12),
        RewardSticker("sticker_shape", "Shape Explorer", "आकार खोजी", "🔺", "Completed shape matching!", "आकार मिलान पूरा किया!", 15),
        RewardSticker("sticker_memory", "Brain Champ", "मस्तिष्क विजेता", "🧠", "Completed Memory match game!", "मेमोरी गेम पूरा किया!", 20),
        RewardSticker("sticker_tracing", "Perfect Writer", "सुलेखक", "✍️", "Traced A-Z or 0-9 beautifully!", "ए-जेड या ०-९ को सुंदर लिखा!", 25)
    )

    val habitList = listOf(
        HabitItem("teeth", "Brush Teeth", "दाँत साफ करें", "🪥", "Brush twice a day for a healthy smile!", "स्वस्थ मुस्कान के लिए दिन में दो बार ब्रश करें!"),
        HabitItem("hands", "Wash Hands", "हाथ धोएं", "🧼", "Wash hands before eating to keep germs away!", "कीटाणुओं को दूर रखने के लिए खाने से पहले हाथ धोएं!"),
        HabitItem("sleep", "Sleep on Time", "समय पर सोएं", "🛌", "Go to bed early to stay active all day!", "पूरे दिन सक्रिय रहने के लिए जल्दी सोएं!"),
        HabitItem("help", "Help Others", "दूसरों की मदद करें", "🤝", "Sharing is caring! Help your friends and family.", "साझा करना ही देखभाल है! अपने दोस्तों और परिवार की मदद करें।"),
        HabitItem("exercise", "Play & Exercise", "खेल और व्यायाम", "🏃", "Stay active and strong by playing outdoors!", "बाहर खेलकर सक्रिय और मजबूत रहें!")
    )

    val oppositeList = listOf(
        OppositeItem("size", "Big", "बड़ा", "Small", "छोटा", "🐘", "🐭"),
        OppositeItem("temp", "Hot", "गर्म", "Cold", "ठंडा", "☀️", "❄️"),
        OppositeItem("emotion", "Happy", "खुश", "Sad", "उदास", "😊", "😢"),
        OppositeItem("daynight", "Day", "दिन", "Night", "रात", "☀️", "🌙"),
        OppositeItem("weight", "Heavy", "भारी", "Light", "हल्का", "📦", "🪶")
    )

    val planetList = listOf(
        PlanetItem("mercury", "Mercury", "बुध", "🪐", "0xFF9E9E9E", "Closest planet to the Sun!", "सूर्य के सबसे निकट का ग्रह!"),
        PlanetItem("venus", "Venus", "शुक्र", "🌕", "0xFFFFD54F", "The hottest planet in our solar system!", "हमारे सौरमंडल का सबसे गर्म ग्रह!"),
        PlanetItem("earth", "Earth", "पृथ्वी", "🌍", "0xFF2196F3", "Our beautiful home planet with life!", "जीवन के साथ हमारा सुंदर गृह ग्रह!"),
        PlanetItem("mars", "Mars", "मंगल", "🔴", "0xFFFF5252", "Known as the Red Planet!", "लाल ग्रह के रूप में जाना जाता है!"),
        PlanetItem("jupiter", "Jupiter", "बृहस्पति", "🪐", "0xFFFFB74D", "The largest planet of all!", "सबसे बड़ा ग्रह!"),
        PlanetItem("saturn", "Saturn", "शनि", "🪐", "0xFFFFCC80", "Has beautiful rings of ice and dust!", "बर्फ और धूल के सुंदर वलय हैं!"),
        PlanetItem("uranus", "Uranus", "अरुण", "🪐", "0xFF80DEEA", "An ice giant planet!", "एक बर्फ विशालकाय ग्रह!"),
        PlanetItem("neptune", "Neptune", "वरुण", "🔵", "0xFF3F51B5", "The windy planet far, far away!", "बहुत दूर स्थित हवाओं वाला ग्रह!")
    )

    val instrumentList = listOf(
        InstrumentItem("drum", "Drum", "ढोलक", "🥁", 150),
        InstrumentItem("piano", "Piano", "पियानो", "🎹", 261),
        InstrumentItem("guitar", "Guitar", "गिटार", "🎸", 329),
        InstrumentItem("trumpet", "Trumpet", "तुरही", "🎺", 440),
        InstrumentItem("bell", "Bell", "घंटी", "🔔", 523)
    )
}
