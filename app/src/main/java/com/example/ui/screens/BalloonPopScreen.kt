package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
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
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BalloonPopScreen(
    viewModel: MainViewModel,
    lang: String,
    onBack: () -> Unit
) {
    val isHindi = lang == "hi"
    val balloons = viewModel.activeBalloons

    // Periodically spawn balloons if none exist
    LaunchedEffect(balloons.size) {
        if (balloons.isEmpty()) {
            viewModel.spawnBalloons(6)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isHindi) "गुब्बारा फोड़ें! 🎈" else "Balloon Pop! 🎈",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            viewModel.clearAllBalloons()
                            onBack()
                        },
                        modifier = Modifier.testTag("btn_balloon_back")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    Button(
                        onClick = { viewModel.spawnBalloons(3) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                        modifier = Modifier.testTag("btn_add_balloons")
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "+3", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE0F7FA)) // Beautiful sky blue background
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (isHindi) "गुब्बारों को फोड़ने के लिए छुएं! 🎈" else "Tap balloons to pop them! 🎈",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF006064),
                    textAlign = TextAlign.Center
                )
            }

            // Render balloons scattered across the toy box
            balloons.forEach { balloon ->
                // Basic animated offset for each balloon to float gently up and down
                val infiniteTransition = rememberInfiniteTransition(label = "balloon_float_${balloon.id}")
                val offsetY by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = -30f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = balloon.durationMs, easing = FastOutSlowInEasing),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "offsetY"
                )

                // Place balloon on the screen using relative X coordinate
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp, vertical = 64.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            // Align horizontally based on relative X position percentage
                            .offset(
                                x = (balloon.xPercent * 280).dp,
                                y = (-150 + offsetY).dp // Offset to float
                            )
                            .size(balloon.sizeDp.dp)
                            .clip(CircleShape)
                            .background(Color(balloon.color))
                            .clickable {
                                viewModel.popBalloon(balloon.id)
                            }
                            .testTag("balloon_${balloon.id}"),
                        contentAlignment = Alignment.Center
                    ) {
                        // Glossy balloon effect / knot visual
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxSize().padding(8.dp)
                        ) {
                            // Gloss highlight
                            Box(
                                modifier = Modifier
                                    .size((balloon.sizeDp / 4).dp)
                                    .clip(CircleShape)
                                    .background(Color(0x7FFFFFFF))
                                    .align(Alignment.Start)
                            )
                            
                            // Balloon string tie knot
                            Text(
                                text = "🎈",
                                fontSize = 16.sp,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }
            }
        }
    }
}
