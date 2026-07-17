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
import com.example.data.BirdItem
import com.example.data.LearningData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirdsScreen(
    viewModel: MainViewModel,
    lang: String,
    onBack: () -> Unit
) {
    val isHindi = lang == "hi"
    val birdItems = LearningData.birdList
    var activeBirdIndex by remember { mutableStateOf<Int?>(null) }

    // Flapping / Flight flight-animation variables
    var isFlying by remember { mutableStateOf(false) }

    // Flap wings animation (rapid size change)
    val infiniteTransition = rememberInfiniteTransition(label = "bird_flap")
    val flapScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (isFlying) 1.25f else 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(if (isFlying) 150 else 600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "flap"
    )

    // Flight altitude offset animation
    val altitudeOffset by animateDpAsState(
        targetValue = if (isFlying) (-60).dp else 0.dp,
        animationSpec = tween(1200, easing = EaseInOutQuad),
        finishedListener = {
            isFlying = false
        },
        label = "altitude"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isHindi) "पक्षी और आवाजें 🦜" else "Birds Play 🦜",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("btn_birds_back")
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

                // Selected Bird Section
                if (activeBirdIndex != null) {
                    val bird = birdItems[activeBirdIndex!!]
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)
                            .testTag("bird_soundboard_card"),
                        shape = RoundedCornerShape(28.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F7FA)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            // Flapping / Flying bird emoji
                            Text(
                                text = bird.emoji,
                                fontSize = 84.sp,
                                modifier = Modifier
                                    .offset(y = altitudeOffset)
                                    .scale(flapScale)
                                    .clickable {
                                        isFlying = true
                                        viewModel.speak(
                                            "${bird.nameEn} flies high and says ${bird.soundEn}",
                                            "${bird.nameHi} उड़कर बोला ${bird.soundHi}"
                                        )
                                    }
                                    .padding(12.dp)
                            )

                            Text(
                                text = if (isHindi) bird.nameHi else bird.nameEn,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF006064)
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = if (isHindi) "बोली: ${bird.soundHi}! 🔊" else "Sound: ${bird.soundEn}! 🔊",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF00838F),
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Button(
                                onClick = {
                                    isFlying = true
                                    viewModel.speak(
                                        "${bird.nameEn} says ${bird.soundEn}",
                                        "${bird.nameHi} बोले ${bird.soundHi}"
                                    )
                                    viewModel.addStars(1)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00ACC1)),
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier.testTag("btn_speak_bird")
                            ) {
                                Icon(imageVector = Icons.Default.VolumeUp, contentDescription = "Listen", tint = Color.White)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (isHindi) "आवाज सुनें और उड़ाएं!" else "Fly & Hear Sound!",
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
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F2F1))
                    ) {
                        Text(
                            text = if (isHindi) "पक्षी पर टैप करें, उसे उड़ाएं और उसकी प्यारी चहचहाहट सुनें! 🦜" else "Tap a bird to make it fly, flap its wings, and hear its lovely voice! 🦜",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF00796B),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Birds Grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    items(birdItems.size) { index ->
                        val bird = birdItems[index]
                        val isSelected = activeBirdIndex == index

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(110.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(
                                    Brush.verticalGradient(
                                        colors = if (isSelected) {
                                            listOf(Color(0xFFB2EBF2), Color(0xFF80DEEA))
                                        } else {
                                            listOf(Color(0xFFE0F7FA), Color(0xFFB2EBF2))
                                        }
                                    )
                                )
                                .clickable {
                                    activeBirdIndex = index
                                    isFlying = true
                                    viewModel.speak(
                                        "${bird.nameEn} says ${bird.soundEn}",
                                        "${bird.nameHi} बोले ${bird.soundHi}"
                                    )
                                    viewModel.addStars(1)
                                }
                                .testTag("bird_item_${bird.id}"),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = bird.emoji, fontSize = 42.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = if (isHindi) bird.nameHi else bird.nameEn,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF006064)
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
