package de.syntax_institut.musicapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Gold,
    onPrimary = DeepBlack,
    secondary = Gold,
    onSecondary = DeepBlack,
    background = DeepBlack,
    onBackground = Gold,
    surface = DeepBlack,
    onSurface = Gold,
    error = AlertRed,
    onError = AlertRed,
)

private val LightColorScheme = lightColorScheme(
    primary = LightGreen,
    onPrimary = LightGreen,
    secondary = DarkGreen,
    onSecondary = DeepBlack,
    background = DeepBlack,
    onBackground = LightGreen,
    surface = DeepBlack,
    onSurface = LightGreen,
    error = AlertRed,
    onError = AlertRed,
)

@Composable
fun MusicAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
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
        content = content
    )
}