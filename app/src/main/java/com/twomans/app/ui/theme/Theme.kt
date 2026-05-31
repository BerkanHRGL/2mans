package com.twomans.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = ForestGreen,
    onPrimary = Color.White,
    primaryContainer = ForestGreenMid,
    onPrimaryContainer = Color.White,
    secondary = Gold,
    onSecondary = ForestGreen,
    background = Cream,
    onBackground = ForestGreen,
    surface = WarmWhite,
    onSurface = ForestGreen,
    surfaceVariant = Color(0xFFDDD8CA),
    onSurfaceVariant = ForestGreen,
    outline = ForestGreenMid
)

private val DarkColorScheme = darkColorScheme(
    primary = Gold,
    onPrimary = ForestGreenDeep,
    background = Color(0xFF1A1F0E),
    onBackground = Color(0xFFE8E3D5),
    surface = Color(0xFF242A14),
    onSurface = Color(0xFFE8E3D5),
)

@Composable
fun _2mansTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
