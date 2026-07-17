package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoryScreen(
    viewModel: MainViewModel,
    lang: String,
    onBack: () -> Unit
) {
    val isHindi = lang == "hi"
    val cards = viewModel.memoryCards
    val level = viewModel.memoryLevel
    val matches = viewModel.memoryMatchesCount
    val totalPairs = viewModel.memoryPairsCount

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isHindi) "दिमागी खेल 🧩" else "Memory Match 🧩",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("btn_memory_back")
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            
            // Header stats
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8EAF6))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = if (isHindi) "स्तर: $level (बच्चों का स्तर)" else "Level: $level (Pre-School)",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF1A237E)
                        )
                        Text(
                            text = if (isHindi) "जोड़े मिले: $matches / $totalPairs" else "Pairs Found: $matches / $totalPairs",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF3F51B5)
                        )
                    }
                    
                    Button(
                        onClick = { viewModel.setupMemoryGame() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3F51B5)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.testTag("btn_memory_reset")
                    ) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Restart")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = if (isHindi) "रीसेट" else "Restart", fontWeight = FontWeight.Bold)
                    }
                }
            }

            Text(
                text = if (isHindi) "दो एक जैसे कार्ड खोजें! 🤔" else "Find two identical cards! 🤔",
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Grid of cards
            // Select number of columns dynamically (2 columns for Level 1, 3 for level 2-3, 4 for Level 4)
            val columnsCount = when {
                cards.size <= 4 -> 2
                cards.size <= 8 -> 3
                else -> 4
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(columnsCount),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                items(cards.size) { index ->
                    val card = cards[index]
                    val isRevealed = card.isFlipped || card.isMatched

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                if (isRevealed) {
                                    Brush.verticalGradient(listOf(Color(0xFFE8EAF6), Color(0xFFC5CAE9)))
                                } else {
                                    Brush.verticalGradient(listOf(Color(0xFF3F51B5), Color(0xFF1A237E)))
                                }
                            )
                            .clickable { viewModel.selectMemoryCard(index) }
                            .testTag("memory_card_$index"),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isRevealed) {
                            Text(
                                text = card.emoji,
                                fontSize = 42.sp
                            )
                        } else {
                            Text(
                                text = "⭐",
                                fontSize = 36.sp,
                                color = Color(0xFFFFD54F)
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
