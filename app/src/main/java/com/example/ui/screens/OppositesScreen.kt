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
import com.example.data.OppositeItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OppositesScreen(
    viewModel: MainViewModel,
    lang: String,
    onBack: () -> Unit
) {
    val isHindi = lang == "hi"
    val items = LearningData.oppositeList
    var activeIndex by remember { mutableStateOf<Int?>(0) }

    val infiniteTransition = rememberInfiniteTransition(label = "opposite_bounce")
    val scaleFactor by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(700, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isHindi) "उलटे शब्द ↕️" else "Opposite Words ↕️",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("btn_opposites_back")
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
            if (activeIndex != null) {
                val item = items[activeIndex!!]
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                        .testTag("opposite_main_card"),
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Word 1 (Left)
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    viewModel.speak(item.word1En, item.word1Hi)
                                    viewModel.addStars(1)
                                }
                        ) {
                            Text(
                                text = item.emoji1,
                                fontSize = 64.sp,
                                modifier = Modifier.scale(scaleFactor)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = if (isHindi) item.word1Hi else item.word1En,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFFE65100)
                            )
                        }

                        // Vs Label
                        Text(
                            text = "⚡",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFB74D)
                        )

                        // Word 2 (Right)
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    viewModel.speak(item.word2En, item.word2Hi)
                                    viewModel.addStars(1)
                                }
                        ) {
                            Text(
                                text = item.emoji2,
                                fontSize = 38.sp, // intentionally smaller than big item for visual opposite, or general
                                modifier = Modifier.scale(if (item.id == "size") 0.85f else scaleFactor)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = if (isHindi) item.word2Hi else item.word2En,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFFD84315)
                            )
                        }
                    }
                }
            }

            Text(
                text = if (isHindi) "अंतर देखने के लिए दबाएं! 👉" else "Tap a pair to compare opposites! 👉",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Grid of all Opposite Pairs
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                itemsIndexed(items) { index, item ->
                    val isSelected = activeIndex == index
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                if (isSelected) {
                                    Brush.verticalGradient(listOf(Color(0xFFFFCC80), Color(0xFFFF9800)))
                                } else {
                                    Brush.verticalGradient(listOf(Color.White, Color(0xFFFFF8E1)))
                                }
                            )
                            .clickable {
                                activeIndex = index
                                viewModel.speak(
                                    "${item.word1En} and ${item.word2En}",
                                    "${item.word1Hi} और ${item.word2Hi}"
                                )
                            }
                            .testTag("opposite_item_card_$index"),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = item.emoji1, fontSize = 28.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "↔️",
                                fontSize = 14.sp,
                                color = if (isSelected) Color.White else Color.Gray
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = item.emoji2, fontSize = 24.sp)
                        }
                    }
                }
            }
        }
    }
}
