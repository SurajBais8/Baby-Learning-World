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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.LearningData
import com.example.data.VegetableItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VegetablesScreen(
    viewModel: MainViewModel,
    lang: String,
    onBack: () -> Unit
) {
    val isHindi = lang == "hi"
    val vegetableItems = LearningData.vegetableList
    var activeVegIndex by remember { mutableStateOf<Int?>(null) }

    // Bounce and Rotate states on clicking
    var isJumping by remember { mutableStateOf(false) }

    val jumpOffset by animateDpAsState(
        targetValue = if (isJumping) (-40).dp else 0.dp,
        animationSpec = tween(500, easing = EaseOutBack),
        finishedListener = { isJumping = false },
        label = "veg_jump"
    )

    val rotateAngle by animateFloatAsState(
        targetValue = if (isJumping) 360f else 0f,
        animationSpec = tween(600, easing = EaseInOutCubic),
        label = "veg_rotate"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isHindi) "सब्जियां सीखें 🥦" else "Vegetables Play 🥦",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("btn_vegetables_back")
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

                // Selected Vegetable Card
                if (activeVegIndex != null) {
                    val veg = vegetableItems[activeVegIndex!!]
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)
                            .testTag("vegetable_soundboard_card"),
                        shape = RoundedCornerShape(28.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            // Jumping / Rotating vegetable emoji
                            Text(
                                text = veg.emoji,
                                fontSize = 84.sp,
                                modifier = Modifier
                                    .offset(y = jumpOffset)
                                    .rotate(rotateAngle)
                                    .clickable {
                                        isJumping = true
                                        viewModel.speak(veg.nameEn, veg.nameHi)
                                    }
                                    .padding(12.dp)
                            )

                            Text(
                                text = if (isHindi) veg.nameHi else veg.nameEn,
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF1B5E20)
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Button(
                                onClick = {
                                    isJumping = true
                                    viewModel.speak(
                                        "This is a healthy vegetable called ${veg.nameEn}",
                                        "यह एक स्वास्थ्यवर्धक सब्जी है जिसे ${veg.nameHi} कहते हैं"
                                    )
                                    viewModel.addStars(1)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier.testTag("btn_speak_vegetable")
                            ) {
                                Icon(imageVector = Icons.Default.VolumeUp, contentDescription = "Listen", tint = Color.White)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (isHindi) "नाम सुनें!" else "Listen Name!",
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
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
                    ) {
                        Text(
                            text = if (isHindi) "सब्जी पर टैप करें, उसे उछालें और उसका नाम सीखें! 🥦" else "Tap a vegetable to make it jump, rotate, and learn its name! 🥦",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E7D32),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Vegetables Grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    items(vegetableItems.size) { index ->
                        val veg = vegetableItems[index]
                        val isSelected = activeVegIndex == index

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(95.dp)
                                .clip(RoundedCornerShape(18.dp))
                                .background(
                                    Brush.verticalGradient(
                                        colors = if (isSelected) {
                                            listOf(Color(0xFFC8E6C9), Color(0xFFA5D6A7))
                                        } else {
                                            listOf(Color(0xFFE8F5E9), Color(0xFFC8E6C9))
                                        }
                                    )
                                )
                                .clickable {
                                    activeVegIndex = index
                                    isJumping = true
                                    viewModel.speak(veg.nameEn, veg.nameHi)
                                    viewModel.addStars(1)
                                }
                                .testTag("vegetable_item_${veg.id}"),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = veg.emoji, fontSize = 38.sp)
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = if (isHindi) veg.nameHi else veg.nameEn,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1B5E20),
                                    textAlign = TextAlign.Center
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
