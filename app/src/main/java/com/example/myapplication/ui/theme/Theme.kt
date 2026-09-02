package com.example.myapplication.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

private val AppShapes = Shapes(
    extraLarge = RoundedCornerShape(28.dp),
    large = RoundedCornerShape(16.dp),
    medium = RoundedCornerShape(12.dp),
    small = RoundedCornerShape(8.dp)
)

private val DarkColorScheme = darkColorScheme(
    primary = AppPrimaryBlue,
    secondary = ZeptoPink,
    tertiary = ZeptoGreen,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    surfaceVariant = Color(0xFF2C2C2C),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFFE1E1E1),
    onSurface = Color(0xFFE1E1E1),
    onSurfaceVariant = Color(0xFFBDBDBD),
    outline = Color(0xFF444444)
)

private val LightColorScheme = lightColorScheme(
    primary = AppPrimaryBlue,
    secondary = ZeptoPink,
    tertiary = ZeptoGreen,
    background = Color.White,
    surface = Color.White,
    surfaceVariant = Color(0xFFF5F5F5),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    onSurfaceVariant = Color(0xFF49454F),
    outline = Color(0xFF79747E),
    secondaryContainer = AppLightBlue,
    onSecondaryContainer = AppPrimaryBlue
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Default to Dark Mode
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Disable dynamic color to maintain branding
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = AppShapes,
        content = content
    )
}