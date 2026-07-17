package com.example.ui.screens

import android.view.MotionEvent
import androidx.compose.animation.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

sealed class DrawingAction {
    data class PathAction(
        val points: List<Offset>,
        val color: Color,
        val brushSize: Float
    ) : DrawingAction()

    data class StampAction(
        val emoji: String,
        val position: Offset,
        val size: Float
    ) : DrawingAction()
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun DrawingScreen(
    viewModel: MainViewModel,
    lang: String,
    onBack: () -> Unit
) {
    val isHindi = lang == "hi"

    // Colors list for child
    val colorOptions = listOf(
        Color(0xFFFF1744), // Red
        Color(0xFF2979FF), // Blue
        Color(0xFF00E676), // Green
        Color(0xFFFFEA00), // Yellow
        Color(0xFFFF4081), // Pink
        Color(0xFFAA00FF), // Purple
        Color(0xFFFF9100), // Orange
        Color(0xFF8D6E63), // Brown
        Color(0xFF212121)  // Black
    )

    // Stamp stickers list
    val stampStickers = listOf(
        "⭐️", "🐶", "🐱", "🦁", "🦊", "🍎", "🎈", "🚀", "🦄", "🌈", "🎨", "🏆", "🌸"
    )

    // Current state configurations
    var selectedColor by remember { mutableStateOf(colorOptions[0]) }
    var selectedBrushSize by remember { mutableStateOf(16f) } // thin=8f, medium=16f, thick=32f
    var activeStamp by remember { mutableStateOf<String?>(null) } // if non-null, placing sticker

    // Actions stacks
    val drawingActions = remember { mutableStateListOf<DrawingAction>() }
    val redoStack = remember { mutableStateListOf<DrawingAction>() }

    // Live points of current drawing stroke
    val currentPoints = remember { mutableStateListOf<Offset>() }

    // Save overlay trigger
    var showSavedDialog by remember { mutableStateOf(false) }

    fun undo() {
        if (drawingActions.isNotEmpty()) {
            val removed = drawingActions.removeAt(drawingActions.lastIndex)
            redoStack.add(removed)
            viewModel.speak("Undo!", "पूर्ववत करें!")
        }
    }

    fun redo() {
        if (redoStack.isNotEmpty()) {
            val restored = redoStack.removeAt(redoStack.lastIndex)
            drawingActions.add(restored)
            viewModel.speak("Redo!", "फिर से करें!")
        }
    }

    fun clearCanvas() {
        drawingActions.clear()
        redoStack.clear()
        currentPoints.clear()
        viewModel.speak("Canvas Cleared!", "कैनवास साफ़ हो गया!")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isHindi) "कला और ड्राइंग 🎨" else "Kids Painting 🎨",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("btn_drawing_back")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    // Save drawing button
                    Button(
                        onClick = {
                            showSavedDialog = true
                            viewModel.addStars(15)
                            viewModel.spawnBalloons(12)
                            viewModel.speak(
                                "Wow! Beautiful painting saved to gallery! Fifteen stars reward!",
                                "वाह! बहुत सुंदर चित्र गैलरी में सहेजा गया! आपके लिए पंद्रह सितारे!"
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .testTag("btn_drawing_save")
                    ) {
                        Icon(imageVector = Icons.Default.Save, contentDescription = "Save Drawing", modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = if (isHindi) "सहेजें" else "Save 📸", fontSize = 12.sp, fontWeight = FontWeight.Bold)
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
            // Interactive Stamp stickers selection carousel
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFECEFF1))
            ) {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = if (isHindi) "स्टिकर स्टाम्प लगाएं 👇" else "Select a Sticker Stamp 👇",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF455A64),
                        modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
                    )

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        item {
                            // Pencil button to return to brush mode
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(if (activeStamp == null) Color(0xFFFF9800) else Color.White)
                                    .clickable {
                                        activeStamp = null
                                        viewModel.speak("Pencil Mode", "पेंसिल मोड")
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Brush Mode",
                                    tint = if (activeStamp == null) Color.White else Color.Gray
                                )
                            }
                        }

                        items(stampStickers) { sticker ->
                            val isSelected = activeStamp == sticker
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(if (isSelected) Color(0xFFFF9800) else Color.White)
                                    .border(
                                        if (isSelected) 3.dp else 0.dp,
                                        Color.White,
                                        CircleShape
                                    )
                                    .clickable {
                                        activeStamp = sticker
                                        viewModel.speakDirect(sticker)
                                    }
                                    .testTag("stamp_$sticker"),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = sticker, fontSize = 24.sp)
                            }
                        }
                    }
                }
            }

            // Main Canvas Area
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .border(4.dp, Color(0xFFFFCC80), RoundedCornerShape(28.dp))
                    .testTag("drawing_canvas_card"),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                            .pointerInteropFilter { motionEvent ->
                                val point = Offset(motionEvent.x, motionEvent.y)
                                if (activeStamp != null) {
                                    // Stamp Action on touch down
                                    if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                                        drawingActions.add(
                                            DrawingAction.StampAction(
                                                emoji = activeStamp!!,
                                                position = point,
                                                size = selectedBrushSize * 3.5f // scales sticker based on brush size
                                            )
                                        )
                                        redoStack.clear()
                                        viewModel.speakDirect(activeStamp!!)
                                    }
                                } else {
                                    // Pencil/Path drawing Action
                                    when (motionEvent.action) {
                                        MotionEvent.ACTION_DOWN -> {
                                            currentPoints.add(point)
                                        }
                                        MotionEvent.ACTION_MOVE -> {
                                            currentPoints.add(point)
                                        }
                                        MotionEvent.ACTION_UP -> {
                                            if (currentPoints.isNotEmpty()) {
                                                drawingActions.add(
                                                    DrawingAction.PathAction(
                                                        points = currentPoints.toList(),
                                                        color = selectedColor,
                                                        brushSize = selectedBrushSize
                                                    )
                                                )
                                                currentPoints.clear()
                                                redoStack.clear()
                                            }
                                        }
                                    }
                                }
                                true
                            }
                    ) {
                        // Render all saved drawing actions
                        drawingActions.forEach { action ->
                            when (action) {
                                is DrawingAction.PathAction -> {
                                    if (action.points.size > 1) {
                                        val path = Path().apply {
                                            moveTo(action.points.first().x, action.points.first().y)
                                            for (i in 1 until action.points.size) {
                                                lineTo(action.points[i].x, action.points[i].y)
                                            }
                                        }
                                        drawPath(
                                            path = path,
                                            color = action.color,
                                            style = Stroke(width = action.brushSize, cap = StrokeCap.Round)
                                        )
                                    }
                                }
                                is DrawingAction.StampAction -> {
                                    // Render sticker emoji at coordinates
                                    // In Compose drawScope we can use native canvas or draw it directly using drawText,
                                    // but we can also use drawing instructions or custom rendering.
                                    // For standard emojis in Compose, using native canvas is straightforward.
                                    drawContext.canvas.nativeCanvas.drawText(
                                        action.emoji,
                                        action.position.x - (action.size / 2),
                                        action.position.y + (action.size / 2),
                                        android.graphics.Paint().apply {
                                            textSize = action.size
                                        }
                                    )
                                }
                            }
                        }

                        // Render active drawing path stroke
                        if (currentPoints.size > 1) {
                            val activePath = Path().apply {
                                moveTo(currentPoints.first().x, currentPoints.first().y)
                                for (i in 1 until currentPoints.size) {
                                    lineTo(currentPoints[i].x, currentPoints[i].y)
                                }
                            }
                            drawPath(
                                path = activePath,
                                color = selectedColor,
                                style = Stroke(width = selectedBrushSize, cap = StrokeCap.Round)
                            )
                        }
                    }

                    // Watermark / Indicator
                    Text(
                        text = if (isHindi) "कलाकार का कैनवास 🎨" else "Artist Playground 🎨",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0x339E9E9E),
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Brush configurations and actions bar
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
            ) {
                Column(
                    modifier = Modifier.padding(10.dp)
                ) {
                    // Row 1: Brush Size and Undo/Redo/Clear Controls
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Size selector buttons
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf(8f, 16f, 32f).forEach { size ->
                                val sizeLabel = when(size) {
                                    8f -> "Thin"
                                    16f -> "Mid"
                                    else -> "Thick"
                                }
                                val isSelected = selectedBrushSize == size
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { selectedBrushSize = size },
                                    label = { Text(sizeLabel, fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                                )
                            }
                        }

                        // Undo, Redo, Clear buttons
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            IconButton(
                                onClick = { undo() },
                                enabled = drawingActions.isNotEmpty(),
                                modifier = Modifier.testTag("btn_undo")
                            ) {
                                Icon(imageVector = Icons.Default.Undo, contentDescription = "Undo")
                            }

                            IconButton(
                                onClick = { redo() },
                                enabled = redoStack.isNotEmpty(),
                                modifier = Modifier.testTag("btn_redo")
                            ) {
                                Icon(imageVector = Icons.Default.Redo, contentDescription = "Redo")
                            }

                            IconButton(
                                onClick = { clearCanvas() },
                                enabled = drawingActions.isNotEmpty() || currentPoints.isNotEmpty(),
                                modifier = Modifier.testTag("btn_clear")
                            ) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "Clear Canvas")
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Row 2: Color Palette row
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(colorOptions) { color ->
                            val isSelected = selectedColor == color && activeStamp == null
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(color)
                                    .border(
                                        if (isSelected) 3.dp else 0.dp,
                                        Color.White,
                                        CircleShape
                                    )
                                    .clickable {
                                        selectedColor = color
                                        activeStamp = null // switch back to brush
                                        viewModel.speak("Color Selected", "रंग चुना")
                                    }
                                    .testTag("color_paint_${color.value}")
                            )
                        }
                    }
                }
            }

            // Image Saved Confirmation Dialog
            if (showSavedDialog) {
                AlertDialog(
                    onDismissRequest = { showSavedDialog = false },
                    title = {
                        Text(
                            text = if (isHindi) "शानदार चित्र! 🎨🌟" else "Masterpiece Saved! 🎨🌟",
                            fontWeight = FontWeight.Black
                        )
                    },
                    text = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "🎉", fontSize = 56.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = if (isHindi) "बधाई हो! आपकी ड्राइंग गैलरी में सुरक्षित सहेज ली गई है और आपने 15 सितारे जीते हैं!" else "Congratulations! Your artwork is saved in the gallery, and you earned 15 Reward Stars!",
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp
                            )
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = { showSavedDialog = false }
                        ) {
                            Text(text = if (isHindi) "शानदार!" else "Awesome!")
                        }
                    }
                )
            }
        }
    }
}
