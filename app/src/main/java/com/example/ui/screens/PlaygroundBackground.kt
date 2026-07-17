package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.sin

@Composable
fun PlaygroundBackground(
    content: @Composable BoxScope.() -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "playground_bg")

    // Cloud 1 animation (horizontal translation)
    val cloudX1 by infiniteTransition.animateFloat(
        initialValue = -100f,
        targetValue = 600f,
        animationSpec = infiniteRepeatable(
            animation = tween(18000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "cloud1"
    )

    // Cloud 2 animation
    val cloudX2 by infiniteTransition.animateFloat(
        initialValue = 500f,
        targetValue = -200f,
        animationSpec = infiniteRepeatable(
            animation = tween(22000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "cloud2"
    )

    // Butterfly 1 animation
    val butterflyY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 30f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "butterflyY"
    )

    // Sparkle scale animation
    val sparkleScale by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = EaseInOutCirc),
            repeatMode = RepeatMode.Reverse
        ),
        label = "sparkleScale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.background.copy(alpha = 0.85f)
                    )
                )
            )
    ) {
        // Draw decorative Rainbow in background corner
        Canvas(modifier = Modifier.fillMaxSize()) {
            val colors = listOf(
                Color(0xFFFF5252).copy(alpha = 0.08f),
                Color(0xFFFF9800).copy(alpha = 0.08f),
                Color(0xFFFFEB3B).copy(alpha = 0.08f),
                Color(0xFF4CAF50).copy(alpha = 0.08f),
                Color(0xFF2196F3).copy(alpha = 0.08f),
                Color(0xFF9C27B0).copy(alpha = 0.08f)
            )
            for (i in colors.indices) {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(colors[i], Color.Transparent),
                        center = Offset(0f, 0f),
                        radius = 400f + (i * 40f)
                    ),
                    radius = 400f + (i * 40f),
                    center = Offset(0f, 0f)
                )
            }
        }

        // Floating clouds
        Text(
            text = "☁️",
            fontSize = 64.sp,
            modifier = Modifier
                .offset(x = cloudX1.dp, y = 80.dp)
        )

        Text(
            text = "☁️",
            fontSize = 48.sp,
            modifier = Modifier
                .offset(x = cloudX2.dp, y = 220.dp)
        )

        // Animated butterflies
        Text(
            text = "🦋",
            fontSize = 32.sp,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 40.dp, top = 120.dp)
                .offset(y = butterflyY.dp)
        )

        Text(
            text = "🦋",
            fontSize = 24.sp,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 20.dp, bottom = 180.dp)
                .offset(y = (-butterflyY).dp)
        )

        // Sparkle points
        Text(
            text = "✨",
            fontSize = 24.sp,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 60.dp, top = 150.dp)
                .offset(y = (butterflyY * 0.5f).dp)
        )

        Text(
            text = "✨",
            fontSize = 20.sp,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 50.dp, bottom = 100.dp)
                .offset(y = (-butterflyY * 0.7f).dp)
        )

        // Render main content on top of background elements
        Box(modifier = Modifier.fillMaxSize()) {
            content()
        }
    }
}
