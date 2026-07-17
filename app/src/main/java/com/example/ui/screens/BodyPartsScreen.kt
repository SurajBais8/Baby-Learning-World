package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.BodyPartItem
import com.example.data.LearningData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BodyPartsScreen(
    viewModel: MainViewModel,
    lang: String,
    onBack: () -> Unit
) {
    val isHindi = lang == "hi"
    val bodyPartItems = LearningData.bodyPartsList
    var activePartIndex by remember { mutableStateOf<Int?>(null) }

    // Pulse animation for active body part
    val infiniteTransition = rememberInfiniteTransition(label = "body_part_pulse")
    val scaleFactor by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isHindi) "शरीर के अंग 🙆" else "Body Parts Play 🙆",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("btn_body_parts_back")
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
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Interactive Body Model Showcase
                if (activePartIndex != null) {
                    val part = bodyPartItems[activePartIndex!!]
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)
                            .testTag("body_part_soundboard_card"),
                        shape = RoundedCornerShape(28.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = part.emoji,
                                fontSize = 84.sp,
                                modifier = Modifier
                                    .scale(scaleFactor)
                                    .clickable {
                                        viewModel.speak(part.nameEn, part.nameHi)
                                    }
                                    .padding(12.dp)
                            )

                            Text(
                                text = if (isHindi) part.nameHi else part.nameEn,
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFFE65100)
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Button(
                                onClick = {
                                    viewModel.speak(
                                        "This is my ${part.nameEn}",
                                        "यह मेरा ${part.nameHi} है"
                                    )
                                    viewModel.addStars(1)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier.testTag("btn_speak_body_part")
                            ) {
                                Icon(imageVector = Icons.Default.VolumeUp, contentDescription = "Listen", tint = Color.White)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (isHindi) "उच्चारण सुनें" else "Listen Voice",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                } else {
                    Spacer(modifier = Modifier.height(12.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
                    ) {
                        Text(
                            text = if (isHindi) "शरीर के किसी अंग पर टैप करें और उसका नाम और उपयोग सीखें! 🙆" else "Tap a body part to hear its friendly pronunciation and learn about it! 🙆",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFE65100),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Body Parts Grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    items(bodyPartItems.size) { index ->
                        val part = bodyPartItems[index]
                        val isSelected = activePartIndex == index

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(110.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(
                                    Brush.verticalGradient(
                                        colors = if (isSelected) {
                                            listOf(Color(0xFFFFCC80), Color(0xFFFFB74D))
                                        } else {
                                            listOf(Color(0xFFFFF3E0), Color(0xFFFFE0B2))
                                        }
                                    )
                                )
                                .clickable {
                                    activePartIndex = index
                                    viewModel.speak(part.nameEn, part.nameHi)
                                    viewModel.addStars(1)
                                }
                                .testTag("body_part_item_${part.id}"),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = part.emoji, fontSize = 42.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = if (isHindi) part.nameHi else part.nameEn,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFE65100)
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
