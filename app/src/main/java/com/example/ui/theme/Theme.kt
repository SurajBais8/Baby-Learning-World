package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = Color(0xFFB39DDB),
    secondary = Color(0xFFFFCC80),
    tertiary = Color(0xFFD1C4E9),
    background = Color(0xFF231F1A),
    surface = Color(0xFF2D2821),
    onPrimary = Color(0xFF231F1A),
    onSecondary = Color(0xFF231F1A),
    onBackground = Color(0xFFEFEBE9),
    onSurface = Color(0xFFEFEBE9)
  )

private val LightColorScheme =
  lightColorScheme(
    primary = Color(0xFF7C4DFF),
    secondary = Color(0xFFFF9100),
    tertiary = Color(0xFF9575CD),
    background = Color(0xFFFDF7E7),
    surface = Color(0xFFFFFFFF),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color(0xFF4E342E),
    onSurface = Color(0xFF4E342E)
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Disabling dynamic colors to let our customized "Natural Tones" theme shine through
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
