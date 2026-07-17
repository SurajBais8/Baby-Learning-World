package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
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
import com.example.data.HabitItem
import com.example.data.LearningData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitsScreen(
    viewModel: MainViewModel,
    lang: String,
    onBack: () -> Unit
) {
    val isHindi = lang == "hi"
    val habitItems = LearningData.habitList
    var activeIndex by remember { mutableStateOf<Int?>(0) }

    val infiniteTransition = rememberInfiniteTransition(label = "habit_bounce")
    val scaleFactor by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.12f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isHindi) "अच्छी आदतें 😇" else "Good Habits 😇",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("btn_habits_back")
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
            // Selected habit soundboard
            if (activeIndex != null) {
                val habit = habitItems[activeIndex!!]
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                        .testTag("habit_detail_card"),
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = habit.emoji,
                            fontSize = 80.sp,
                            modifier = Modifier
                                .scale(scaleFactor)
                                .clickable {
                                    viewModel.speak(
                                        "${habit.nameEn}! ${habit.descEn}",
                                        "${habit.nameHi}! ${habit.descHi}"
                                    )
                                }
                                .padding(8.dp)
                        )

                        Text(
                            text = if (isHindi) habit.nameHi else habit.nameEn,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF2E7D32)
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = if (isHindi) habit.descHi else habit.descEn,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF1B5E20),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                viewModel.speak(
                                    "${habit.nameEn}! ${habit.descEn}",
                                    "${habit.nameHi}! ${habit.descHi}"
                                )
                                viewModel.addStars(1)
                                viewModel.spawnBalloons(2)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.testTag("btn_speak_habit")
                        ) {
                            Icon(imageVector = Icons.Default.VolumeUp, contentDescription = "Listen", tint = Color.White)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isHindi) "आवाज सुनें" else "Listen Voice",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Text(
                text = if (isHindi) "सभी आदतें सीखें! 👇" else "Tap a Habit to learn! 👇",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Grid of all good habits
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                itemsIndexed(habitItems) { index, habit ->
                    val isSelected = activeIndex == index
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                if (isSelected) {
                                    Brush.verticalGradient(listOf(Color(0xFFC8E6C9), Color(0xFF81C784)))
                                } else {
                                    Brush.verticalGradient(listOf(Color.White, Color(0xFFF1F8E9)))
                                }
                            )
                            .clickable {
                                activeIndex = index
                                viewModel.speak(habit.nameEn, habit.nameHi)
                            }
                            .testTag("habit_card_$index"),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = habit.emoji, fontSize = 32.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (isHindi) habit.nameHi else habit.nameEn,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Black,
                                color = if (isSelected) Color(0xFF1B5E20) else Color(0xFF33691E),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}
