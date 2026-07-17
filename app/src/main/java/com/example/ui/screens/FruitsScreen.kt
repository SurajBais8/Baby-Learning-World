package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.FruitItem
import com.example.data.LearningData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FruitsScreen(
    viewModel: MainViewModel,
    lang: String,
    onBack: () -> Unit
) {
    val isHindi = lang == "hi"
    var selectedTab by remember { mutableStateOf(0) } // 0: Learn, 1: Basket Sort Game

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isHindi) "फल और सब्जियां 🍎" else "Fruits & Veggies 🍎",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("btn_fruits_back")
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
                    modifier = Modifier.testTag("tab_learn_fruits")
                ) {
                    Text(
                        text = if (isHindi) "पहचानें" else "Learn Items",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(12.dp)
                    )
                }
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    modifier = Modifier.testTag("tab_fruits_game")
                ) {
                    Text(
                        text = if (isHindi) "टोकरी खेल" else "Sorting Game",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            if (selectedTab == 0) {
                // Learn Grid
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (isHindi) "फल या सब्जी छुएं और नाम सुनें!" else "Tap to hear fruit or vegetable names!",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(LearningData.fruitList) { item ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(Color(0xFFFFF3E0), Color(0xFFFFCC80))
                                        )
                                    )
                                    .clickable {
                                        val typeEn = if (item.isFruit) "is a fruit" else "is a vegetable"
                                        val typeHi = if (item.isFruit) "एक फल है" else "एक सब्जी है"
                                        viewModel.speak(
                                            "${item.nameEn} $typeEn",
                                            "${item.nameHi} $typeHi"
                                        )
                                        viewModel.addStars(1)
                                    }
                                    .testTag("fruit_item_${item.id}"),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(text = item.emoji, fontSize = 42.sp)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = if (isHindi) item.nameHi else item.nameEn,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFE65100),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                // Sorting Basket Game
                val currentItem = viewModel.fruitVeggieItem

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
                            .weight(1.5f),
                        shape = RoundedCornerShape(32.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFDE7)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = if (isHindi) "इसे सही टोकरी में डालें! 🛒" else "Sort into the correct basket! 🛒",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFFF57F17),
                                textAlign = TextAlign.Center
                            )

                            // Giant target item display
                            Text(
                                text = currentItem.emoji,
                                fontSize = 90.sp,
                                modifier = Modifier
                                    .padding(vertical = 12.dp)
                                    .testTag("sorting_target_emoji")
                            )

                            Text(
                                text = if (isHindi) currentItem.nameHi else currentItem.nameEn,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF5D4037)
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            // Interactive Baskets Row
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                // Fruit Basket
                                Card(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable { viewModel.submitFruitSortingAnswer(true) }
                                        .testTag("btn_basket_fruit"),
                                    shape = RoundedCornerShape(20.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(text = "🍎", fontSize = 36.sp)
                                        Text(
                                            text = if (isHindi) "फल की टोकरी" else "Fruit Basket",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFFC62828),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }

                                // Vegetable Basket
                                Card(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable { viewModel.submitFruitSortingAnswer(false) }
                                        .testTag("btn_basket_veggie"),
                                    shape = RoundedCornerShape(20.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(text = "🥦", fontSize = 36.sp)
                                        Text(
                                            text = if (isHindi) "सब्जी टोकरी" else "Veggie Basket",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF2E7D32),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            // Answer Feedback
                            when (viewModel.fruitVeggieSuccess) {
                                true -> {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(6.dp)
                                    ) {
                                        Icon(imageVector = Icons.Default.Star, contentDescription = "Star", tint = Color(0xFFFFB300), modifier = Modifier.size(32.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = if (isHindi) "बहुत बढ़िया! +५ ⭐" else "Super Sort! +5 ⭐",
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF4CAF50)
                                        )
                                    }
                                }
                                false -> {
                                    Text(
                                        text = if (isHindi) "ओह! ये गलत टोकरी है। फिर सोचें! ❌" else "Oops! Wrong Basket. Try Again! ❌",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFF44336),
                                        textAlign = TextAlign.Center
                                    )
                                }
                                null -> {
                                    Text(
                                        text = if (isHindi) "फल है या सब्जी? टोकरी छुएं!" else "Is it fruit or veggie? Tap basket!",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Next Sorting Match Button
                    Button(
                        onClick = { viewModel.generateNewFruitSortingGame() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .testTag("btn_fruits_next")
                    ) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Next")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isHindi) "अगला आइटम ➔" else "Next Item ➔",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
