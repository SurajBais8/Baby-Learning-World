package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.example.data.PlanetItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolarSystemScreen(
    viewModel: MainViewModel,
    lang: String,
    onBack: () -> Unit
) {
    val isHindi = lang == "hi"
    val planets = LearningData.planetList
    var activeIndex by remember { mutableStateOf<Int?>(2) } // default to Earth!

    // Orbit or rotate animation
    val infiniteTransition = rememberInfiniteTransition(label = "orbit")
    val planetScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orbit_scale"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isHindi) "सौर मंडल 🌌" else "Solar System 🌌",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 22.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("btn_planets_back")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0D1B2A)
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0B0C10)) // Deep cosmic space black
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            
            // Selected planet details
            if (activeIndex != null) {
                val planet = planets[activeIndex!!]
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                        .testTag("planet_detail_card"),
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1B263B)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        // Floating Planet
                        Text(
                            text = planet.emoji,
                            fontSize = 90.sp,
                            modifier = Modifier
                                .scale(planetScale)
                                .clickable {
                                    viewModel.speak(
                                        "${planet.nameEn}! ${planet.funFactEn}",
                                        "${planet.nameHi}! ${planet.funFactHi}"
                                    )
                                    viewModel.addStars(2)
                                    viewModel.spawnBalloons(3)
                                }
                                .padding(12.dp)
                        )

                        Text(
                            text = if (isHindi) planet.nameHi else planet.nameEn,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFFE0E1DD)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = if (isHindi) planet.funFactHi else planet.funFactEn,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFFA5A5A5),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                viewModel.speak(
                                    "${planet.nameEn}! ${planet.funFactEn}",
                                    "${planet.nameHi}! ${planet.funFactHi}"
                                )
                                viewModel.addStars(1)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E1DD), contentColor = Color(0xFF0D1B2A)),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.testTag("btn_speak_planet")
                        ) {
                            Text(
                                text = if (isHindi) "ब्रह्मांड की जानकारी सुनें" else "Listen Planet Fact",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Text(
                text = if (isHindi) "किसी भी ग्रह को छुएं! 🚀" else "Explore the planets! 🚀",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF808080),
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Horizontal or grid listing of planets
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                itemsIndexed(planets) { index, planet ->
                    val isSelected = activeIndex == index
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(85.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(
                                if (isSelected) Color(0xFF415A77) else Color(0xFF1B263B)
                            )
                            .clickable {
                                activeIndex = index
                                viewModel.speak(planet.nameEn, planet.nameHi)
                            }
                            .testTag("planet_card_$index"),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = planet.emoji, fontSize = 28.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (isHindi) planet.nameHi else planet.nameEn,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.White else Color(0xFFE0E1DD)
                            )
                        }
                    }
                }
            }
        }
    }
}
