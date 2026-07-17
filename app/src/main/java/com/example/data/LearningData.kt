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
        FruitItem("carrot", "Carrot", "गाजर", "🥕", false),
        FruitItem("broccoli", "Broccoli", "ब्रोकोली", "🥦", false),
        FruitItem("potato", "Potato", "आलू", "🥔", false),
        FruitItem("tomato", "Tomato", "टमाटर", "🍅", false)
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
        RewardSticker("sticker_vehicle", "Super Driver", "सुपर ड्राइवर", "🚗", "Tapped all vehicle sounds!", "सभी गाड़ियों की आवाजें सुनीं!", 12),
        RewardSticker("sticker_shape", "Shape Explorer", "आकार खोजी", "🔺", "Completed shape matching!", "आकार मिलान पूरा किया!", 15),
        RewardSticker("sticker_memory", "Brain Champ", "मस्तिष्क विजेता", "🧠", "Completed Memory match game!", "मेमोरी गेम पूरा किया!", 20),
        RewardSticker("sticker_tracing", "Perfect Writer", "सुलेखक", "✍️", "Traced A-Z or 0-9 beautifully!", "ए-जेड या ०-९ को सुंदर लिखा!", 25)
    )
}
