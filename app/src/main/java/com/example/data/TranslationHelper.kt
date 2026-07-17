package com.example.data

object TranslationHelper {
    // English to Spanish, French, Arabic translations
    private val dictionary = mapOf(
        // ABC items
        "Apple" to Triple("Manzana", "Pomme", "تفاح"),
        "Ball" to Triple("Pelota", "Ballon", "كرة"),
        "Cat" to Triple("Gato", "Chat", "قط"),
        "Dog" to Triple("Perro", "Chien", "كلب"),
        "Elephant" to Triple("Elefante", "Éléphant", "فيل"),
        "Fish" to Triple("Pez", "Poisson", "سمكة"),
        "Grapes" to Triple("Uvas", "Raisins", "عنب"),
        "Horse" to Triple("Caballo", "Cheval", "حصان"),
        "Ice Cream" to Triple("Helado", "Glace", "آيس كريم"),
        "Jug" to Triple("Jarra", "Cruche", "إبريق"),
        "Kite" to Triple("Cometa", "Cerf-volant", "طائرة ورقية"),
        "Lion" to Triple("León", "Lion", "أسد"),
        "Monkey" to Triple("Mono", "Singe", "قرد"),
        "Nest" to Triple("Nido", "Nid", "عش"),
        "Orange" to Triple("Naranja", "Orange", "برتقال"),
        "Parrot" to Triple("Loro", "Perroquet", "ببغاء"),
        "Queen" to Triple("Reina", "Reine", "ملكة"),
        "Rabbit" to Triple("Conejo", "Lapin", "أرنب"),
        "Sun" to Triple("Sol", "Soleil", "شمس"),
        "Train" to Triple("Tren", "Train", "قطار"),
        "Umbrella" to Triple("Paraguas", "Parapluie", "مظلة"),
        "Van" to Triple("Furgoneta", "Camionnette", "عربة"),
        "Watch" to Triple("Reloj", "Montre", "ساعة"),
        "Xylophone" to Triple("Xilófono", "Xylophone", "زيلوفون"),
        "Yak" to Triple("Yak", "Yak", "ياك"),
        "Zebra" to Triple("Cebra", "Zèbre", "حمار وحشي"),

        // Colors
        "Red" to Triple("Rojo", "Rouge", "أحمر"),
        "Blue" to Triple("Azul", "Bleu", "أزرق"),
        "Green" to Triple("Verde", "Vert", "أخضر"),
        "Yellow" to Triple("Amarillo", "Jaune", "أصفر"),
        "Purple" to Triple("Morado", "Violet", "بنفسجي"),
        "Pink" to Triple("Rosa", "Rose", "وردي"),

        // Animals
        "Cow" to Triple("Vaca", "Vache", "بقرة"),
        "Sheep" to Triple("Oveja", "Mouton", "خروف"),
        "Frog" to Triple("Rana", "Grenouille", "ضفدع"),
        "Duck" to Triple("Pato", "Canard", "بطة"),
        "Pig" to Triple("Cerdo", "Cochon", "خنزير"),

        // Birds
        "Crow" to Triple("Cuervo", "Corbeau", "غراب"),
        "Peacock" to Triple("Pavo real", "Paon", "طاووس"),
        "Eagle" to Triple("Águila", "Aigle", "نسر"),
        "Sparrow" to Triple("Gorrión", "Moineau", "عصفور"),
        "Owl" to Triple("Búho", "Hibou", "بومة"),
        "Hen" to Triple("Gallina", "Poule", "دجاجة"),
        "Pigeon" to Triple("Paloma", "Pigeon", "حمامة"),
        "Swan" to Triple("Cisne", "Cygne", "بجعة"),

        // Fruits
        "Banana" to Triple("Plátano", "Banane", "موز"),
        "Mango" to Triple("Mango", "Mangue", "مانجو"),
        "Strawberry" to Triple("Fresa", "Fraise", "فراولة"),
        "Watermelon" to Triple("Sandía", "Pastèque", "بطيخ"),
        "Pineapple" to Triple("Piña", "Ananas", "أناناس"),
        "Peach" to Triple("Melocotón", "Pêche", "خوخ"),
        "Cherry" to Triple("Cereza", "Cerise", "كرز"),
        "Pear" to Triple("Pera", "Poire", "كمثرى"),
        "Kiwi" to Triple("Kiwi", "Kiwi", "كيوي"),
        "Pomegranate" to Triple("Granada", "Grenade", "رمان"),
        "Lemon" to Triple("Limón", "Citron", "ليمون"),
        "Melon" to Triple("Melón", "Melon", "شمام"),
        "Blueberry" to Triple("Arándano", "Myrtille", "توت أزرق"),
        "Raspberry" to Triple("Frambuesa", "Framboise", "توت العليق"),
        "Blackberry" to Triple("Mora", "Mûre", "توت أسود"),
        "Coconut" to Triple("Coco", "Noix de coco", "جوز الهند"),
        "Papaya" to Triple("Papaya", "Papaye", "بابايا"),
        "Fig" to Triple("Higo", "Figue", "تين"),
        "Plum" to Triple("Ciruela", "Prune", "برقوق"),
        "Apricot" to Triple("Albaricoque", "Abricot", "مشمش"),
        "Grapefruit" to Triple("Pomelo", "Pamplemousse", "جريب فروت"),
        "Lychee" to Triple("Lichi", "Litchi", "ليتشي"),

        // Vegetables
        "Carrot" to Triple("Zanahoria", "Carotte", "جزر"),
        "Broccoli" to Triple("Brócoli", "Brocolis", "بروكلي"),
        "Potato" to Triple("Patata", "Pomme de terre", "بطاطس"),
        "Tomato" to Triple("Tomate", "Tomate", "طماطم"),
        "Corn" to Triple("Maíz", "Maïs", "ذرة"),
        "Eggplant" to Triple("Berenjena", "Aubergine", "باذنجان"),
        "Pepper" to Triple("Pimiento", "Poivron", "فلفل"),
        "Cucumber" to Triple("Pepino", "Concombre", "خيار"),
        "Lettuce" to Triple("Lechuga", "Laitue", "خس"),
        "Onion" to Triple("Cebolla", "Oignon", "بصل"),
        "Garlic" to Triple("Ajo", "Ail", "ثوم"),
        "Mushroom" to Triple("Champiñón", "Champignon", "فطر"),
        "Sweet Potato" to Triple("Batata", "Patate douce", "بطاطا حلوة"),
        "Pumpkin" to Triple("Calabaza", "Citrouille", "قرع"),
        "Avocado" to Triple("Aguacate", "Avocat", "أفوكادو"),
        "Peas" to Triple("Guisantes", "Petits pois", "بازلاء"),
        "Radish" to Triple("Rábano", "Radis", "فجل"),
        "Cabbage" to Triple("Repollo", "Chou", "ملفوف"),
        "Spinach" to Triple("Espinaca", "Épinard", "سبانخ"),
        "Ginger" to Triple("Jengibre", "Gingembre", "زنجبيل"),
        "Chili" to Triple("Chile", "Piment", "فلفل حار"),
        "Beetroot" to Triple("Remolacha", "Betterave", "شمندر"),
        "Celery" to Triple("Apio", "Céleri", "كرفس"),
        "Zucchini" to Triple("Calabacín", "Courgette", "كوسة"),
        "Asparagus" to Triple("Espárrago", "Asperge", "هليون"),

        // Shapes
        "Circle" to Triple("Círculo", "Cercle", "دائرة"),
        "Square" to Triple("Cuadrado", "Carré", "مربع"),
        "Triangle" to Triple("Triángulo", "Triangle", "مثلث"),
        "Star" to Triple("Estrella", "Étoile", "نجمة"),
        "Heart" to Triple("Corazón", "Cœur", "قلب"),
        "Oval" to Triple("Óvalo", "Ovale", "بيضاوي"),

        // Body Parts
        "Head" to Triple("Cabeza", "Tête", "رأس"),
        "Eyes" to Triple("Ojos", "Yeux", "عيون"),
        "Ears" to Triple("Orejas", "Oreilles", "آذان"),
        "Nose" to Triple("Nariz", "Nez", "أنف"),
        "Mouth" to Triple("Boca", "Bouche", "فم"),
        "Hand" to Triple("Mano", "Main", "يد"),
        "Foot" to Triple("Pie", "Pied", "قدم"),
        "Hair" to Triple("Cabello", "Cheveux", "شعر"),
        "Teeth" to Triple("Dientes", "Dents", "أسنان"),
        "Tongue" to Triple("Lengua", "Langue", "لسان"),

        // Vehicles
        "Car" to Triple("Coche", "Voiture", "سيارة"),
        "Train" to Triple("Tren", "Train", "قطار"),
        "Plane" to Triple("Avión", "Avion", "طائرة"),
        "Bus" to Triple("Autobús", "Bus", "حافلة"),
        "Police Car" to Triple("Coche de policía", "Voiture de police", "سيارة شرطة"),
        "Rocket" to Triple("Cohete", "Fusée", "صاروخ"),
        "Boat" to Triple("Barco", "Bateau", "قارب"),
        "Tractor" to Triple("Tractor", "Tracteur", "جرار")
    )

    // Translates the given English text to target lang
    fun translate(enText: String, lang: String): String {
        if (lang == "en") return enText
        
        // Simple word-by-word or exact match translation
        val exactMatch = dictionary[enText]
        if (exactMatch != null) {
            return when (lang) {
                "es" -> exactMatch.first
                "fr" -> exactMatch.second
                "ar" -> exactMatch.third
                else -> enText
            }
        }
        
        // Return original if no dictionary match
        return enText
    }

    // UI labels translation helper
    fun getLabel(key: String, lang: String): String {
        val labels = mapOf(
            "app_title" to mapOf("en" to "Baby World 🍼", "hi" to "किड्स वर्ल्ड 🍼", "es" to "Mundo Bebé 🍼", "fr" to "Monde de Bébé 🍼", "ar" to "عالم الطفل 🍼"),
            "back" to mapOf("en" to "Back", "hi" to "पीछे", "es" to "Atrás", "fr" to "Retour", "ar" to "رجوع"),
            "listen" to mapOf("en" to "Listen", "hi" to "सुनें", "es" to "Escuchar", "fr" to "Écouter", "ar" to "استمع"),
            "level" to mapOf("en" to "Level", "hi" to "स्तर", "es" to "Nivel", "fr" to "Niveau", "ar" to "مستوى"),
            "score" to mapOf("en" to "Score", "hi" to "स्कोर", "es" to "Puntuación", "fr" to "Score", "ar" to "نقاط"),
            "stars" to mapOf("en" to "Stars", "hi" to "सितारे", "es" to "Estrellas", "fr" to "Étoiles", "ar" to "نجوم"),
            "streak" to mapOf("en" to "Streak", "hi" to "लगातार दिन", "es" to "Racha", "fr" to "Série", "ar" to "سلسلة نشاط")
        )
        return labels[key]?.get(lang) ?: labels[key]?.get("en") ?: key
    }
}
