package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AbcItem
import com.example.data.LearningData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AbcScreen(
    viewModel: MainViewModel,
    lang: String,
    onBack: () -> Unit
) {
    val isHindi = lang == "hi"
    val abcItems = LearningData.abcList
    var selectedIndex by remember { mutableStateOf<Int?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isHindi) "एबीसी सीखें 🔤" else "ABC Learning 🔤",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("btn_abc_back")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back to home"
                        )
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
            if (selectedIndex == null) {
                // ABC Grid Screen
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (isHindi) "अक्षर पर टैप करें!" else "Tap on a letter!",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    ) {
                        items(abcItems.size) { index ->
                            val item = abcItems[index]
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(Color(0xFFFFF3E0), Color(0xFFFFE0B2))
                                        )
                                    )
                                    .clickable {
                                        selectedIndex = index
                                        viewModel.speak(
                                            "${item.letter}. ${item.letter} for ${item.nameEn}",
                                            "${item.letter}. ${item.letter} से ${item.nameHi}"
                                        )
                                        viewModel.addStars(1)
                                    }
                                    .testTag("abc_item_${item.letter}"),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = item.letter,
                                        fontSize = 36.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Color(0xFFE65100)
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = item.emoji,
                                        fontSize = 24.sp
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            } else {
                // ABC Detail Reader with swipe options
                val index = selectedIndex!!
                val item = abcItems[index]

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        shape = RoundedCornerShape(32.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            // Massive Letter Display
                            Text(
                                text = item.letter,
                                fontSize = 100.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFFFF8F00),
                                textAlign = TextAlign.Center
                            )

                            // Giant Emoji Image
                            Text(
                                text = item.emoji,
                                fontSize = 120.sp,
                                modifier = Modifier
                                    .padding(vertical = 12.dp)
                                    .clickable {
                                        viewModel.speak(
                                            item.nameEn,
                                            item.nameHi
                                        )
                                    }
                                    .testTag("abc_detail_emoji")
                            )

                            // Localized words
                            Text(
                                text = item.nameEn,
                                fontSize = 36.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF3E2723)
                            )

                            Text(
                                text = item.nameHi,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFE65100),
                                modifier = Modifier.padding(top = 4.dp)
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            // Sound Pronunciation Button
                            Button(
                                onClick = {
                                    viewModel.speak(
                                        "${item.letter} for ${item.nameEn}",
                                        "${item.letter} से ${item.nameHi}"
                                    )
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFB300)),
                                shape = RoundedCornerShape(20.dp),
                                modifier = Modifier.testTag("btn_speak_letter")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.VolumeUp,
                                    contentDescription = "Speak",
                                    tint = Color.White
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (isHindi) "सुनें 🔊" else "Listen 🔊",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Previous / Close / Next navigation bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Previous Letter
                        Button(
                            onClick = {
                                val prevIndex = if (index > 0) index - 1 else abcItems.size - 1
                                selectedIndex = prevIndex
                                val prevItem = abcItems[prevIndex]
                                viewModel.speak(
                                    "${prevItem.letter}. ${prevItem.nameEn}",
                                    "${prevItem.letter}. ${prevItem.nameHi}"
                                )
                            },
                            enabled = index > 0,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.testTag("btn_prev_letter")
                        ) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Prev")
                        }

                        // Back to Grid
                        Button(
                            onClick = { selectedIndex = null },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF795548)),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.testTag("btn_back_to_grid")
                        ) {
                            Text(
                                text = if (isHindi) "सभी अक्षर" else "All Letters",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        // Next Letter
                        Button(
                            onClick = {
                                val nextIndex = if (index < abcItems.size - 1) index + 1 else 0
                                selectedIndex = nextIndex
                                val nextItem = abcItems[nextIndex]
                                viewModel.speak(
                                    "${nextItem.letter}. ${nextItem.nameEn}",
                                    "${nextItem.letter}. ${nextItem.nameHi}"
                                )
                            },
                            enabled = index < abcItems.size - 1,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.testTag("btn_next_letter")
                        ) {
                            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Next")
                        }
                    }
                }
            }
        }
    }
}
