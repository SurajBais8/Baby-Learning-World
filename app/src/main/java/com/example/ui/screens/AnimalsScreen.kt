package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.example.data.AnimalItem
import com.example.data.LearningData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalsScreen(
    viewModel: MainViewModel,
    lang: String,
    onBack: () -> Unit
) {
    val isHindi = lang == "hi"
    val animalItems = LearningData.animalList
    var activeAnimalIndex by remember { mutableStateOf<Int?>(null) }

    // Bounce animation for active animal emoji
    val infiniteTransition = rememberInfiniteTransition(label = "animal_bounce")
    val scaleFactor by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isHindi) "पशु और आवाजें 🦁" else "Animals Play 🦁",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("btn_animals_back")
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            
            // Selected Animal soundboard section
            if (activeAnimalIndex != null) {
                val animal = animalItems[activeAnimalIndex!!]
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                        .testTag("animal_soundboard_card"),
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        // Bouncing Animal emoji
                        Text(
                            text = animal.emoji,
                            fontSize = 80.sp,
                            modifier = Modifier
                                .scale(scaleFactor)
                                .clickable {
                                    viewModel.speak(
                                        "${animal.nameEn} says ${animal.soundEn}",
                                        "${animal.nameHi} बोले ${animal.soundHi}"
                                    )
                                }
                                .padding(12.dp)
                        )

                        Text(
                            text = if (isHindi) animal.nameHi else animal.nameEn,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF4A148C)
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        // Sound textual mimicry
                        Text(
                            text = if (isHindi) "आवाज: ${animal.soundHi}! 🔊" else "Sound: ${animal.soundEn}! 🔊",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF7B1FA2),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                viewModel.speak(
                                    "${animal.nameEn} says ${animal.soundEn}",
                                    "${animal.nameHi} बोले ${animal.soundHi}"
                                )
                                viewModel.addStars(1)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C27B0)),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.testTag("btn_speak_animal")
                        ) {
                            Icon(imageVector = Icons.Default.VolumeUp, contentDescription = "Listen", tint = Color.White)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isHindi) "आवाज सुनें" else "Listen Voice",
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
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9C4))
                ) {
                    Text(
                        text = if (isHindi) "पशु पर टैप करें और उसकी आवाज और नाम सुनें! 🐶" else "Tap an animal to hear its friendly sound and name! 🐶",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFF57F17),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Animal Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                items(animalItems.size) { index ->
                    val animal = animalItems[index]
                    val isSelected = activeAnimalIndex == index
                    
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                Brush.verticalGradient(
                                    colors = if (isSelected) {
                                        listOf(Color(0xFFE1BEE7), Color(0xFFD1C4E9))
                                    } else {
                                        listOf(Color(0xFFF3E5F5), Color(0xFFE1BEE7))
                                    }
                                )
                            )
                            .clickable {
                                activeAnimalIndex = index
                                viewModel.speak(
                                    "${animal.nameEn} says ${animal.soundEn}",
                                    "${animal.nameHi} बोले ${animal.soundHi}"
                                )
                                viewModel.addStars(1)
                            }
                            .testTag("animal_item_${animal.id}"),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = animal.emoji, fontSize = 42.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (isHindi) animal.nameHi else animal.nameEn,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF4A148C)
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
