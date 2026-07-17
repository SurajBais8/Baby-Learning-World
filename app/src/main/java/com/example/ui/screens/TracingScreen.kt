package com.example.ui.screens

import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun TracingScreen(
    viewModel: MainViewModel,
    lang: String,
    onBack: () -> Unit
) {
    val isHindi = lang == "hi"
    val alphabet = ('A'..'Z').map { it.toString() }
    val digits = (0..9).map { it.toString() }

    val activeChar = viewModel.tracingTargetChar
    val isLetter = viewModel.tracingIsLetter

    // Finger drawing points list
    val currentPoints = remember { mutableStateListOf<Offset>() }
    val finishedPaths = remember { mutableStateListOf<List<Offset>>() }

    // Clear brush
    fun clearCanvas() {
        currentPoints.clear()
        finishedPaths.clear()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isHindi) "सुलेखक तारा ✍️" else "Tracer Star ✍️",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("btn_tracing_back")
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
            
            // Selector bar between letters and numbers
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        viewModel.changeTracingChar("A", true)
                        clearCanvas()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("btn_tracing_letters"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isLetter) Color(0xFFFF9800) else Color(0xFFFFCC80)
                    )
                ) {
                    Text(text = "A-Z", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }

                Button(
                    onClick = {
                        viewModel.changeTracingChar("1", false)
                        clearCanvas()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("btn_tracing_numbers"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (!isLetter) Color(0xFFFF9800) else Color(0xFFFFCC80)
                    )
                ) {
                    Text(text = "0-9", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }

            // Carousel character list
            val sourceList = if (isLetter) alphabet else digits
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(sourceList) { char ->
                    val isSelected = activeChar == char
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) Color(0xFFFF9800) else Color(0xFFFFE0B2))
                            .clickable {
                                viewModel.changeTracingChar(char, isLetter)
                                clearCanvas()
                                viewModel.speakDirect(char)
                            }
                            .testTag("char_selector_$char"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = char,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Black,
                            color = if (isSelected) Color.White else Color(0xFFE65100)
                        )
                    }
                }
            }

            // Main Tracing Board Area
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .border(4.dp, Color(0xFFFFCC80), RoundedCornerShape(28.dp))
                    .testTag("tracing_canvas_card"),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Dotted text guide behind drawing
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = activeChar,
                            fontSize = 240.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0x1F795548), // very faint gray-brown dotted look
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                drawStyle = Stroke(
                                    width = 6f,
                                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 15f), 0f)
                                )
                            )
                        )
                    }

                    // User touch drawing Canvas
                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                            .pointerInteropFilter { motionEvent ->
                                when (motionEvent.action) {
                                    MotionEvent.ACTION_DOWN -> {
                                        currentPoints.add(Offset(motionEvent.x, motionEvent.y))
                                    }
                                    MotionEvent.ACTION_MOVE -> {
                                        currentPoints.add(Offset(motionEvent.x, motionEvent.y))
                                    }
                                    MotionEvent.ACTION_UP -> {
                                        if (currentPoints.isNotEmpty()) {
                                            finishedPaths.add(currentPoints.toList())
                                            currentPoints.clear()
                                        }
                                    }
                                }
                                true
                            }
                    ) {
                        // Draw all finalized finger paths
                        finishedPaths.forEach { pathPoints ->
                            if (pathPoints.size > 1) {
                                val path = Path().apply {
                                    moveTo(pathPoints.first().x, pathPoints.first().y)
                                    for (i in 1 until pathPoints.size) {
                                        lineTo(pathPoints[i].x, pathPoints[i].y)
                                    }
                                }
                                drawPath(
                                    path = path,
                                    color = Color(0xFFFF5722),
                                    style = Stroke(width = 16f, cap = StrokeCap.Round)
                                )
                            }
                        }

                        // Draw current active touch path
                        if (currentPoints.size > 1) {
                            val activePath = Path().apply {
                                moveTo(currentPoints.first().x, currentPoints.first().y)
                                for (i in 1 until currentPoints.size) {
                                    lineTo(currentPoints[i].x, currentPoints[i].y)
                                }
                            }
                            drawPath(
                                path = activePath,
                                color = Color(0xFFFF5722),
                                style = Stroke(width = 16f, cap = StrokeCap.Round)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Brush actions bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Clear Brush
                Button(
                    onClick = { clearCanvas() },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .testTag("btn_tracing_clear"),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9E9E9E)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(imageVector = Icons.Default.CleaningServices, contentDescription = "Clear")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = if (isHindi) "साफ़ करें" else "Clear 🧹", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }

                // Complete tracing & claim star
                Button(
                    onClick = {
                        if (finishedPaths.isNotEmpty()) {
                            viewModel.addStars(10)
                            viewModel.spawnBalloons(10)
                            viewModel.speak(
                                "Wow! Perfect tracing of $activeChar! Ten stars for you!",
                                "वाह! $activeChar को बेहतरीन लिखा! आपके लिए दस सितारे!"
                            )
                            clearCanvas()
                            viewModel.markTracingCompleted()
                        } else {
                            viewModel.speak(
                                "Draw first, buddy!",
                                "पहले लिखो तो सही, दोस्त!"
                            )
                        }
                    },
                    modifier = Modifier
                        .weight(1.2f)
                        .height(56.dp)
                        .testTag("btn_tracing_done"),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(imageVector = Icons.Default.Done, contentDescription = "Done")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = if (isHindi) "पूरा हुआ!" else "Done! ⭐", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
