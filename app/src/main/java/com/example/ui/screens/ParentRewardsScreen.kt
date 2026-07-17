package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
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
import com.example.data.RewardSticker
import com.example.data.UserProgress

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParentRewardsScreen(
    progress: UserProgress,
    viewModel: MainViewModel,
    lang: String,
    onBack: () -> Unit
) {
    val isHindi = lang == "hi"
    var showParentGate by remember { mutableStateOf(false) }
    var isParentUnlocked by remember { mutableStateOf(false) }

    // State for math quiz gate
    var firstTerm by remember { mutableStateOf((3..8).random()) }
    var secondTerm by remember { mutableStateOf((2..6).random()) }
    var inputAnswer by remember { mutableStateOf("") }
    var gateError by remember { mutableStateOf(false) }

    val stickers = LearningData.rewardStickers
    val unlockedList = progress.unlockedStickers.split(",")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isHindi) "पुरस्कार और डैशबोर्ड 🏅" else "Parent & Rewards 🏅",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("btn_parent_back")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    if (!isParentUnlocked) {
                        Button(
                            onClick = { showParentGate = true },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                            modifier = Modifier.testTag("btn_parent_gate_open")
                        ) {
                            Icon(imageVector = Icons.Default.Lock, contentDescription = "Parent Gate", modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = if (isHindi) "पेरेंट जोन" else "Parent Zone", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    } else {
                        Button(
                            onClick = { isParentUnlocked = false },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935)),
                            modifier = Modifier.testTag("btn_parent_lock")
                        ) {
                            Text(text = if (isHindi) "बाहर निकलें" else "Exit Parent", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
        ) {
            if (!isParentUnlocked) {
                // Sticker Cabinet child sandbox
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "🏅", fontSize = 36.sp, modifier = Modifier.padding(end = 12.dp))
                            Column {
                                Text(
                                    text = if (isHindi) "स्टीकर एल्बम ⭐" else "My Sticker Cabinet ⭐",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFF2E7D32)
                                )
                                Text(
                                    text = if (isHindi) "आपके पास ${progress.totalStars} सितारे हैं। नए स्टीकर अनलॉक करें!" else "You have ${progress.totalStars} Stars! Unlock cool stickers!",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF388E3C)
                                )
                            }
                        }
                    }

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(stickers) { sticker ->
                            val isUnlocked = unlockedList.contains(sticker.id)
                            
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp)
                                    .clickable {
                                        if (isUnlocked) {
                                            viewModel.speak(
                                                "Congratulations! You unlocked ${sticker.nameEn} sticker!",
                                                "बधाई हो! आपने ${sticker.nameHi} स्टीकर अनलॉक कर लिया!"
                                            )
                                        } else {
                                            viewModel.unlockStickerByStars(sticker.id, sticker.starCost)
                                        }
                                    }
                                    .testTag("sticker_card_${sticker.id}"),
                                shape = RoundedCornerShape(20.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isUnlocked) Color(0xFFFFF9C4) else Color(0xFFF5F5F5)
                                )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    // Sticker Badge/Lock Representation
                                    Box(
                                        modifier = Modifier
                                            .size(56.dp)
                                            .clip(CircleShape)
                                            .background(
                                                if (isUnlocked) Color(0xFFFFF59D) else Color(0xFFE0E0E0)
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (isUnlocked) {
                                            Text(text = sticker.emoji, fontSize = 36.sp)
                                        } else {
                                            Icon(imageVector = Icons.Default.Lock, contentDescription = "Locked", tint = Color.Gray)
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = if (isHindi) sticker.nameHi else sticker.nameEn,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Black,
                                        textAlign = TextAlign.Center
                                    )

                                    if (!isUnlocked) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center,
                                            modifier = Modifier.padding(top = 4.dp)
                                        ) {
                                            Icon(imageVector = Icons.Default.Star, contentDescription = "Stars", tint = Color(0xFFFFB300), modifier = Modifier.size(12.dp))
                                            Spacer(modifier = Modifier.width(2.dp))
                                            Text(
                                                text = "${sticker.starCost}",
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF757575)
                                            )
                                        }
                                    } else {
                                        Text(
                                            text = if (isHindi) "अनलॉक हुआ!" else "Unlocked!",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF4CAF50),
                                            modifier = Modifier.padding(top = 4.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                // Parent dashboard with stats and clean control panels
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = if (isHindi) "👨‍👩‍👧 अभिभावक डैशबोर्ड" else "👨‍👩‍👧 Parent Dashboard",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = if (isHindi) "सीखने की प्रगति" else "Learning Analytics",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF33691E)
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = if (isHindi) "दैनिक स्ट्रीक:" else "Daily Streak:", fontWeight = FontWeight.Bold)
                                Text(text = "${progress.currentStreak} 🔥", fontWeight = FontWeight.Black, color = Color(0xFFE65100))
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = if (isHindi) "अर्जित कुल सितारे:" else "Stars Collected:", fontWeight = FontWeight.Bold)
                                Text(text = "${progress.totalStars} ⭐", fontWeight = FontWeight.Black, color = Color(0xFFF57F17))
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = if (isHindi) "अनलॉक किए गए स्टीकर:" else "Unlocked Stickers:", fontWeight = FontWeight.Bold)
                                Text(text = "${unlockedList.size} / ${stickers.size}", fontWeight = FontWeight.Black, color = Color(0xFF388E3C))
                            }
                        }
                    }

                    // Completed Modules Log list
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = if (isHindi) "पूरे किए गए विषय 🏆" else "Completed Modules 🏆",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            val completed = progress.completedModules.split(",").filter { it.isNotEmpty() }
                            if (completed.isEmpty()) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = if (isHindi) "बच्चे ने अभी तक कोई विषय पूरा नहीं किया है। खेलें और सीखें!" else "No modules fully completed yet. Start learning!",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.Gray,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            } else {
                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(2),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    items(completed) { mod ->
                                        Card(
                                            shape = RoundedCornerShape(12.dp),
                                            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
                                        ) {
                                            Text(
                                                text = "✓ ${mod.uppercase()}",
                                                modifier = Modifier.padding(10.dp),
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF2E7D32),
                                                fontSize = 14.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Parental Lock Gate Modal
            if (showParentGate) {
                AlertDialog(
                    onDismissRequest = { showParentGate = false },
                    title = {
                        Text(
                            text = if (isHindi) "सुरक्षा अभिभावक द्वार 👨‍👩‍👧" else "Secure Parental Gate 👨‍👩‍👧",
                            fontWeight = FontWeight.Black,
                            fontSize = 20.sp
                        )
                    },
                    text = {
                        Column {
                            Text(
                                text = if (isHindi) "अभिभावक क्षेत्र में प्रवेश करने के लिए कृपया इस गणित समस्या को हल करें:" else "To enter the Parent Dashboard, please solve this math question:",
                                fontSize = 14.sp,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                            
                            // Math equation
                            Text(
                                text = "$firstTerm + $secondTerm = ?",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Black,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )

                            OutlinedTextField(
                                value = inputAnswer,
                                onValueChange = { inputAnswer = it },
                                label = { Text(if (isHindi) "जवाब" else "Your Answer") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 12.dp)
                                    .testTag("input_parent_gate_answer")
                            )

                            if (gateError) {
                                Text(
                                    text = if (isHindi) "गलत उत्तर! कृपया फिर से कोशिश करें।" else "Incorrect answer! Please try again.",
                                    color = Color.Red,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                val expected = firstTerm + secondTerm
                                if (inputAnswer.trim() == "$expected") {
                                    isParentUnlocked = true
                                    showParentGate = false
                                    gateError = false
                                    inputAnswer = ""
                                    // Speak welcome
                                    viewModel.speak("Welcome to Parent Dashboard", "माता-पिता के क्षेत्र में स्वागत है")
                                } else {
                                    gateError = true
                                    // Regenerate terms
                                    firstTerm = (3..8).random()
                                    secondTerm = (2..6).random()
                                }
                            },
                            modifier = Modifier.testTag("btn_parent_gate_submit")
                        ) {
                            Text(text = if (isHindi) "पुष्टि करें" else "Submit")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { showParentGate = false }
                        ) {
                            Text(text = if (isHindi) "रद्द करें" else "Cancel")
                        }
                    }
                )
            }
        }
    }
}
