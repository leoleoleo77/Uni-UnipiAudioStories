package com.leo.unipiaudiostories.ui.theme

import android.app.Activity
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
import androidx.core.graphics.toColorInt

private val ColorScheme = darkColorScheme(
    primary = Color("#264653".toColorInt()),
    onPrimary = Color("#2a9d8f".toColorInt()),
    secondary = Color("#e9c46a".toColorInt()),
    onSecondary = Color("#e9c46a".toColorInt()),
    tertiary = Color("#e76f51".toColorInt()),
)


@Composable
fun FunTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ColorScheme,
        typography = Typography,
        content = content
    )
}