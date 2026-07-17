package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
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
import com.example.data.ColorItem
import com.example.data.LearningData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorsScreen(
    viewModel: MainViewModel,
    lang: String,
    onBack: () -> Unit
) {
    val isHindi = lang == "hi"
    var selectedTab by remember { mutableStateOf(0) } // 0: Learn Colors, 1: Color Game

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isHindi) "रंगों की दुनिया 🎨" else "Color World 🎨",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("btn_colors_back")
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
        ) {
            // Screen tabs
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.background
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    modifier = Modifier.testTag("tab_learn_colors")
                ) {
                    Text(
                        text = if (isHindi) "रंग सीखें" else "Learn Colors",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(12.dp)
                    )
                }
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    modifier = Modifier.testTag("tab_color_game")
                ) {
                    Text(
                        text = if (isHindi) "रंग का खेल" else "Color Game",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            if (selectedTab == 0) {
                // Color Learning Grid
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (isHindi) "रंग पर टैप करें और नाम सुनें!" else "Tap a color card to listen!",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(LearningData.colorList) { colorItem ->
                            val colorVal = Color(colorItem.colorHex.removePrefix("0x").toLong(16))

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(140.dp)
                                    .clickable {
                                        viewModel.speak(
                                            colorItem.nameEn,
                                            colorItem.nameHi
                                        )
                                        viewModel.addStars(1)
                                    }
                                    .testTag("color_item_${colorItem.id}"),
                                shape = RoundedCornerShape(20.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    // Big Color Circle
                                    Box(
                                        modifier = Modifier
                                            .size(50.dp)
                                            .clip(CircleShape)
                                            .background(colorVal)
                                            .border(2.dp, Color.LightGray, CircleShape)
                                    )
                                    
                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = if (isHindi) colorItem.nameHi else colorItem.nameEn,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Black,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )

                                    // Display example objects
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = colorItem.exampleObjects.joinToString(" "),
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                // Interactive Color Match Game Screen
                val targetColor = viewModel.colorTarget
                val targetColorVal = Color(targetColor.colorHex.removePrefix("0x").toLong(16))

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
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = if (isHindi) "इस रंग को ढूंढें: 🎨" else "Find this color: 🎨",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                textAlign = TextAlign.Center
                            )

                            // Giant target color name display
                            Text(
                                text = if (isHindi) targetColor.nameHi else targetColor.nameEn,
                                fontSize = 48.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF33691E),
                                modifier = Modifier.padding(vertical = 12.dp)
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            // Three Color Choice circles
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                viewModel.colorOptions.forEach { option ->
                                    val optionColor = Color(option.colorHex.removePrefix("0x").toLong(16))

                                    Box(
                                        modifier = Modifier
                                            .size(80.dp)
                                            .clip(CircleShape)
                                            .background(optionColor)
                                            .border(4.dp, Color.White, CircleShape)
                                            .clickable { viewModel.submitColorAnswer(option) }
                                            .testTag("btn_color_option_${option.id}"),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = option.emojiRepresentation,
                                            fontSize = 32.sp
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(32.dp))

                            // Answer Feedback
                            when (viewModel.colorSuccess) {
                                true -> {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(8.dp)
                                    ) {
                                        Icon(imageVector = Icons.Default.Star, contentDescription = "Star", tint = Color(0xFFFFB300), modifier = Modifier.size(36.dp))
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = if (isHindi) "शाबाश! सही रंग! +५ ⭐" else "Great Job! Correct! +5 ⭐",
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF4CAF50)
                                        )
                                    }
                                }
                                false -> {
                                    Text(
                                        text = if (isHindi) "गलत! फिर से सोचें! ❌" else "Wrong! Try Again! ❌",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFF44336)
                                    )
                                }
                                null -> {
                                    Text(
                                        text = if (isHindi) "सही रंग पर टैप करें!" else "Tap the correct color!",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Next Color Match Button
                    Button(
                        onClick = { viewModel.generateNewColorGame() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .testTag("btn_color_next")
                    ) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Next")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isHindi) "अगला सवाल ➔" else "Next Color ➔",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
