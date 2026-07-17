package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.UserProgress

data class ActivityModule(
    val route: String,
    val titleEn: String,
    val titleHi: String,
    val emoji: String,
    val testTag: String
)

private data class ColorProfile(
    val emojiBg: Color,
    val textColor: Color,
    val bottomBorderColor: Color
)

private fun getModuleColorProfile(route: String, isDark: Boolean): ColorProfile {
    return when (route) {
        "abc" -> ColorProfile(
            emojiBg = if (isDark) Color(0x33FFCDD2) else Color(0xFFFFEBEE),
            textColor = if (isDark) Color(0xFFFF8A80) else Color(0xFFE57373),
            bottomBorderColor = if (isDark) Color(0xFFC62828) else Color(0xFFFFCDD2)
        )
        "numbers" -> ColorProfile(
            emojiBg = if (isDark) Color(0x33C8E6C9) else Color(0xFFE8F5E9),
            textColor = if (isDark) Color(0xFFB9F6CA) else Color(0xFF81C784),
            bottomBorderColor = if (isDark) Color(0xFF2E7D32) else Color(0xFFC8E6C9)
        )
        "colors" -> ColorProfile(
            emojiBg = if (isDark) Color(0x33BBDEFB) else Color(0xFFE3F2FD),
            textColor = if (isDark) Color(0xFF82B1FF) else Color(0xFF64B5F6),
            bottomBorderColor = if (isDark) Color(0xFF1565C0) else Color(0xFFBBDEFB)
        )
        "animals" -> ColorProfile(
            emojiBg = if (isDark) Color(0x33FFE0B2) else Color(0xFFFFF3E0),
            textColor = if (isDark) Color(0xFFFFD180) else Color(0xFFFFB74D),
            bottomBorderColor = if (isDark) Color(0xFFEF6C00) else Color(0xFFFFE0B2)
        )
        "fruits" -> ColorProfile(
            emojiBg = if (isDark) Color(0x33F8BBD0) else Color(0xFFFCE4EC),
            textColor = if (isDark) Color(0xFFFF80AB) else Color(0xFFF06292),
            bottomBorderColor = if (isDark) Color(0xFFC2185B) else Color(0xFFF8BBD0)
        )
        "vehicles" -> ColorProfile(
            emojiBg = if (isDark) Color(0x33B2EBF2) else Color(0xFFE0F7FA),
            textColor = if (isDark) Color(0xFF84FFFF) else Color(0xFF4DD0E1),
            bottomBorderColor = if (isDark) Color(0xFF00838F) else Color(0xFFB2EBF2)
        )
        "shapes" -> ColorProfile(
            emojiBg = if (isDark) Color(0x33D1C4E9) else Color(0xFFF3E5F5),
            textColor = if (isDark) Color(0xFFB388FF) else Color(0xFF9575CD),
            bottomBorderColor = if (isDark) Color(0xFF4527A0) else Color(0xFFD1C4E9)
        )
        "birds" -> ColorProfile(
            emojiBg = if (isDark) Color(0x33B2EBF2) else Color(0xFFE0F7FA),
            textColor = if (isDark) Color(0xFF84FFFF) else Color(0xFF00ACC1),
            bottomBorderColor = if (isDark) Color(0xFF006064) else Color(0xFFB2EBF2)
        )
        "vegetables" -> ColorProfile(
            emojiBg = if (isDark) Color(0x33C8E6C9) else Color(0xFFE8F5E9),
            textColor = if (isDark) Color(0xFFB9F6CA) else Color(0xFF4CAF50),
            bottomBorderColor = if (isDark) Color(0xFF1B5E20) else Color(0xFFC8E6C9)
        )
        "body_parts" -> ColorProfile(
            emojiBg = if (isDark) Color(0x33FFE0B2) else Color(0xFFFFF3E0),
            textColor = if (isDark) Color(0xFFFFD180) else Color(0xFFFF9800),
            bottomBorderColor = if (isDark) Color(0xFFE65100) else Color(0xFFFFE0B2)
        )
        "quiz" -> ColorProfile(
            emojiBg = if (isDark) Color(0x33FFF9C4) else Color(0xFFFFFDE7),
            textColor = if (isDark) Color(0xFFFFE57F) else Color(0xFFFBC02D),
            bottomBorderColor = if (isDark) Color(0xFFF57F17) else Color(0xFFFFF9C4)
        )
        "drawing" -> ColorProfile(
            emojiBg = if (isDark) Color(0x33F8BBD0) else Color(0xFFFCE4EC),
            textColor = if (isDark) Color(0xFFFF80AB) else Color(0xFFF06292),
            bottomBorderColor = if (isDark) Color(0xFFC2185B) else Color(0xFFF8BBD0)
        )
        "settings" -> ColorProfile(
            emojiBg = if (isDark) Color(0x33CFD8DC) else Color(0xFFECEFF1),
            textColor = if (isDark) Color(0xFF90A4AE) else Color(0xFF607D8B),
            bottomBorderColor = if (isDark) Color(0xFF37474F) else Color(0xFFCFD8DC)
        )
        "memory" -> ColorProfile(
            emojiBg = if (isDark) Color(0x33C5CAE9) else Color(0xFFE8EAF6),
            textColor = if (isDark) Color(0xFF8C9EFF) else Color(0xFF7986CB),
            bottomBorderColor = if (isDark) Color(0xFF283593) else Color(0xFFC5CAE9)
        )
        "tracing" -> ColorProfile(
            emojiBg = if (isDark) Color(0x33FFF9C4) else Color(0xFFFFFDE7),
            textColor = if (isDark) Color(0xFFFFE57F) else Color(0xFFFBC02D),
            bottomBorderColor = if (isDark) Color(0xFFF57F17) else Color(0xFFFFF9C4)
        )
        "balloon_pop" -> ColorProfile(
            emojiBg = if (isDark) Color(0x33E1BEE7) else Color(0xFFFDF0FF),
            textColor = if (isDark) Color(0xFFEA80FC) else Color(0xFFBA68C8),
            bottomBorderColor = if (isDark) Color(0xFF6A1B9A) else Color(0xFFE1BEE7)
        )
        "habits" -> ColorProfile(
            emojiBg = if (isDark) Color(0x33C8E6C9) else Color(0xFFE8F5E9),
            textColor = if (isDark) Color(0xFFB9F6CA) else Color(0xFF81C784),
            bottomBorderColor = if (isDark) Color(0xFF2E7D32) else Color(0xFFC8E6C9)
        )
        "opposites" -> ColorProfile(
            emojiBg = if (isDark) Color(0x33FFE0B2) else Color(0xFFFFF3E0),
            textColor = if (isDark) Color(0xFFFFD180) else Color(0xFFFFB74D),
            bottomBorderColor = if (isDark) Color(0xFFEF6C00) else Color(0xFFFFE0B2)
        )
        "planets" -> ColorProfile(
            emojiBg = if (isDark) Color(0x33C5CAE9) else Color(0xFFE8EAF6),
            textColor = if (isDark) Color(0xFF8C9EFF) else Color(0xFF7986CB),
            bottomBorderColor = if (isDark) Color(0xFF283593) else Color(0xFFC5CAE9)
        )
        "instruments" -> ColorProfile(
            emojiBg = if (isDark) Color(0x33BBDEFB) else Color(0xFFE3F2FD),
            textColor = if (isDark) Color(0xFF82B1FF) else Color(0xFF64B5F6),
            bottomBorderColor = if (isDark) Color(0xFF1565C0) else Color(0xFFBBDEFB)
        )
        else -> ColorProfile( // parent_rewards
            emojiBg = if (isDark) Color(0x33B2DFDB) else Color(0xFFE0F2F1),
            textColor = if (isDark) Color(0xFFA7FFEB) else Color(0xFF4DB6AC),
            bottomBorderColor = if (isDark) Color(0xFF00695C) else Color(0xFFB2DFDB)
        )
    }
}

@Composable
fun HomeScreen(
    progress: UserProgress,
    onNavigate: (String) -> Unit,
    onToggleLanguage: () -> Unit,
    onToggleDarkMode: () -> Unit,
    viewModel: MainViewModel
) {
    val isHindi = progress.lang == "hi"
    val isDark = progress.isDarkMode

    // Fun bouncy animation for stars and streak
    val infiniteTransition = rememberInfiniteTransition(label = "bouncy")
    val bounceScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bounce"
    )

    val modules = listOf(
        ActivityModule("abc", "ABC Learning", "एबीसी सीखें", "🔤", "btn_abc"),
        ActivityModule("numbers", "1-100 Numbers", "१-१०० संख्याएं", "🔢", "btn_numbers"),
        ActivityModule("colors", "Color World", "रंगों की दुनिया", "🎨", "btn_colors"),
        ActivityModule("animals", "Animals Play", "पशु और आवाज", "🦁", "btn_animals"),
        ActivityModule("birds", "Birds Play", "पक्षी और आवाज", "🦜", "btn_birds"),
        ActivityModule("vehicles", "Vehicles Zoo", "गाड़ियों का सफर", "🚗", "btn_vehicles"),
        ActivityModule("fruits", "Fruits Play", "फलों का राजा", "🍎", "btn_fruits"),
        ActivityModule("vegetables", "Vegetables Play", "सब्जियां सीखें", "🥦", "btn_vegetables"),
        ActivityModule("shapes", "Fun Shapes", "मजेदार आकार", "🔺", "btn_shapes"),
        ActivityModule("body_parts", "Body Parts", "शरीर के अंग", "🙆", "btn_body_parts"),
        ActivityModule("quiz", "Kids Quiz", "प्रश्नोत्तरी", "❓", "btn_quiz"),
        ActivityModule("drawing", "Drawing Mode", "ड्रॉइंग बोर्ड", "🎨", "btn_drawing"),
        ActivityModule("memory", "Memory Game", "मेमोरी खेल", "🧩", "btn_memory"),
        ActivityModule("balloon_pop", "Balloon Pop", "गुब्बारा फोड़", "🎈", "btn_balloon_pop"),
        ActivityModule("habits", "Good Habits", "अच्छी आदतें", "😇", "btn_habits"),
        ActivityModule("opposites", "Opposites", "उलटे शब्द", "↕️", "btn_opposites"),
        ActivityModule("planets", "Solar System", "सौर मंडल", "🌌", "btn_planets"),
        ActivityModule("instruments", "Instruments", "वाद्ययंत्र", "🥁", "btn_instruments"),
        ActivityModule("parent_rewards", "Rewards Cabinet", "पुरस्कार और सितारे", "🏅", "btn_parent"),
        ActivityModule("settings", "App Settings", "सेटिंग्स", "⚙️", "btn_settings")
    )

    Scaffold(
        topBar = {
            // High-Fidelity Custom Header representing the "Natural Tones" layout
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (isDark) Color(0xFF3E2723) else Color(0xFFFFE58F),
                        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // App icon and brand details
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(Color.White, shape = RoundedCornerShape(16.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "🍼", fontSize = 24.sp)
                        }
                        Column {
                            Text(
                                text = if (isHindi) "किड्स वर्ल्ड" else "BABY WORLD",
                                fontWeight = FontWeight.Black,
                                fontSize = 20.sp,
                                color = if (isDark) Color(0xFFD1C4E9) else Color(0xFF7C4DFF)
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = if (isHindi) "तारा स्तर" else "Stars Level",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isDark) Color(0xFFFFCC80) else Color(0xFFFF9100)
                                )
                                // Visual progress indicator based on stars (e.g., stars modulo 10 or similar)
                                val starsProgress = ((progress.totalStars % 10) / 10f).coerceIn(0.1f, 1f)
                                Box(
                                    modifier = Modifier
                                        .width(60.dp)
                                        .height(6.dp)
                                        .background(Color.White, shape = CircleShape)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .fillMaxWidth(fraction = starsProgress)
                                            .background(if (isDark) Color(0xFFFFCC80) else Color(0xFFFF9100), shape = CircleShape)
                                    )
                                }
                            }
                        }
                    }

                    // Action Buttons: Lang toggle & Theme toggle
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { onToggleLanguage() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = if (isDark) Color(0xFF3E2723) else Color(0xFF7C4DFF)
                            ),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.testTag("btn_lang_toggle")
                        ) {
                            Text(
                                text = if (isHindi) "English 🇬🇧" else "हिंदी 🇮🇳",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black
                            )
                        }

                        IconButton(
                            onClick = { onToggleDarkMode() },
                            modifier = Modifier
                                .size(40.dp)
                                .background(Color.White, shape = CircleShape)
                                .testTag("btn_theme_toggle")
                        ) {
                            Icon(
                                imageVector = if (progress.isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                                contentDescription = if (progress.isDarkMode) "Light mode" else "Dark mode",
                                tint = if (isDark) Color(0xFFFFB300) else Color(0xFF607D8B),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Welcome Hero Message
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = if (isHindi) "चलो खेलें और सीखें!" else "Let's Play & Learn!",
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                color = if (isDark) Color(0xFFFFCC80) else Color(0xFFFF9100),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Score & Streak Panel
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Streak Card
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .scale(bounceScale)
                        .testTag("card_streak"),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isDark) Color(0xFF4E342E) else Color(0xFFFFEAD2)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "🔥",
                            fontSize = 28.sp,
                            modifier = Modifier.padding(end = 6.dp)
                        )
                        Column {
                            Text(
                                text = if (isHindi) "लगातार दिन" else "Streak Days",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isDark) Color(0xFFFFCC80) else Color(0xFFE65100)
                            )
                            Text(
                                text = "${progress.currentStreak}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Black,
                                color = if (isDark) Color(0xFFFFCC80) else Color(0xFFE65100)
                            )
                        }
                    }
                }

                // Stars Card
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .scale(bounceScale)
                        .testTag("card_stars"),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isDark) Color(0xFF5D4037) else Color(0xFFFFF9C4)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "⭐",
                            fontSize = 28.sp,
                            modifier = Modifier.padding(end = 6.dp)
                        )
                        Column {
                            Text(
                                text = if (isHindi) "कुल सितारे" else "Total Stars",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isDark) Color(0xFFFFE082) else Color(0xFFF57F17)
                            )
                            Text(
                                text = "${progress.totalStars}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Black,
                                color = if (isDark) Color(0xFFFFE082) else Color(0xFFF57F17)
                            )
                        }
                    }
                }
            }

            // Grid of Modules
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                items(modules) { module ->
                    val isCompleted = progress.completedModules.split(",").contains(module.route)
                    val profile = getModuleColorProfile(module.route, isDark)

                    // 3D chunky tactile button design styled with Natural Tones
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .background(profile.bottomBorderColor)
                            .clickable {
                                viewModel.speak(
                                    "Let's play " + module.titleEn,
                                    "चलो खेलते हैं " + module.titleHi
                                )
                                onNavigate(module.route)
                            }
                            .testTag(module.testTag)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = 4.dp) // creates tactile 3D effect
                                .background(
                                    color = if (isDark) Color(0xFF2D2821) else Color.White,
                                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp, bottomStart = 20.dp, bottomEnd = 20.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(8.dp)
                            ) {
                                // Emoji enclosed in its beautiful Natural Tone soft background box
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .background(profile.emojiBg, shape = RoundedCornerShape(14.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = module.emoji,
                                        fontSize = 30.sp
                                    )
                                }
                                
                                Spacer(modifier = Modifier.height(6.dp))
                                
                                Text(
                                    text = if (isHindi) module.titleHi else module.titleEn,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Black,
                                    color = profile.textColor,
                                    textAlign = TextAlign.Center
                                )
                                
                                // Completed module checkmark badge
                                if (isCompleted) {
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center,
                                        modifier = Modifier
                                            .background(Color(0xFF4CAF50), RoundedCornerShape(12.dp))
                                            .padding(horizontal = 8.dp, vertical = 2.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "Completed",
                                            tint = Color.White,
                                            modifier = Modifier.size(10.dp)
                                        )
                                        Spacer(modifier = Modifier.width(2.dp))
                                        Text(
                                            text = if (isHindi) "पूरा हुआ" else "Done",
                                            color = Color.White,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Full-width promo block matching the "Daily Quiz / Sticker Cabinet" card in the Design HTML
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.speak(
                                    "Let's visit the Sticker Cabinet!",
                                    "चलो स्टीकर एल्बम देखते हैं!"
                                )
                                onNavigate("parent_rewards")
                            }
                            .testTag("btn_daily_quiz_banner"),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isDark) Color(0xFF512DA8) else Color(0xFF9575CD)
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text(text = "🎁", fontSize = 32.sp)
                                Column {
                                    Text(
                                        text = if (isHindi) "दैनिक उपहार एल्बम" else "Daily Sticker Cabinet",
                                        fontWeight = FontWeight.Black,
                                        fontSize = 16.sp,
                                        color = Color.White
                                    )
                                    Text(
                                        text = if (isHindi) "सितारे इकट्ठा करें और नए स्टीकर पाएं!" else "Collect stars & unlock new stickers!",
                                        fontSize = 12.sp,
                                        color = Color.White.copy(alpha = 0.9f)
                                    )
                                }
                            }
                            Text(text = "➔", fontSize = 24.sp, color = Color.White)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

