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
import com.example.data.LearningData
import com.example.data.VehicleItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehiclesScreen(
    viewModel: MainViewModel,
    lang: String,
    onBack: () -> Unit
) {
    val isHindi = lang == "hi"
    val vehicles = LearningData.vehicleList
    var activeVehicleIndex by remember { mutableStateOf<Int?>(null) }

    // Fun scale animation for the selected vehicle
    val infiniteTransition = rememberInfiniteTransition(label = "vehicle_bounce")
    val scaleFactor by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isHindi) "गाड़ियों का सफर 🚗" else "Vehicles Play 🚗",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("btn_vehicles_back")
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
            
            // Selected vehicle details soundboard
            if (activeVehicleIndex != null) {
                val vehicle = vehicles[activeVehicleIndex!!]
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                        .testTag("vehicle_sound_card"),
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFDE7)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        // Animated bouncing vehicle emoji
                        Text(
                            text = vehicle.emoji,
                            fontSize = 80.sp,
                            modifier = Modifier
                                .scale(scaleFactor)
                                .clickable {
                                    viewModel.speak(
                                        "${vehicle.nameEn} goes ${vehicle.soundTextEn}",
                                        "${vehicle.nameHi} बोले ${vehicle.soundTextHi}"
                                    )
                                }
                                .padding(12.dp)
                        )

                        Text(
                            text = if (isHindi) vehicle.nameHi else vehicle.nameEn,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFFE65100)
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        // Text sound simulation
                        Text(
                            text = if (isHindi) "आवाज: ${vehicle.soundTextHi}! 🔊" else "Sound: ${vehicle.soundTextEn}! 🔊",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFFF57C00),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                viewModel.speak(
                                    "${vehicle.nameEn} goes ${vehicle.soundTextEn}",
                                    "${vehicle.nameHi} बोले ${vehicle.soundTextHi}"
                                )
                                viewModel.addStars(1)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.testTag("btn_speak_vehicle")
                        ) {
                            Icon(imageVector = Icons.Default.VolumeUp, contentDescription = "Listen", tint = Color.White)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isHindi) "भोंपू बजाएं!" else "Sound Horn!",
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
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F7FA))
                ) {
                    Text(
                        text = if (isHindi) "गाड़ी पर टैप करें और उसकी हॉर्न की आवाज सुनें! 🚂" else "Tap any vehicle to hear its horn blow and speed! 🚂",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF006064),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Vehicles list in grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                items(vehicles.size) { index ->
                    val vehicle = vehicles[index]
                    val isSelected = activeVehicleIndex == index
                    
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                Brush.verticalGradient(
                                    colors = if (isSelected) {
                                        listOf(Color(0xFFFFECB3), Color(0xFFFFD54F))
                                    } else {
                                        listOf(Color(0xFFE0F7FA), Color(0xFFB2EBF2))
                                    }
                                )
                            )
                            .clickable {
                                activeVehicleIndex = index
                                viewModel.speak(
                                    "${vehicle.nameEn} goes ${vehicle.soundTextEn}",
                                    "${vehicle.nameHi} बोले ${vehicle.soundTextHi}"
                                )
                                viewModel.addStars(1)
                            }
                            .testTag("vehicle_item_${vehicle.id}"),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = vehicle.emoji, fontSize = 42.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (isHindi) vehicle.nameHi else vehicle.nameEn,
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
