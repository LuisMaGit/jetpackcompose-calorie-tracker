package com.luisma.core_ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

@Composable
fun CalorieTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        CTrackerColors.night()
    } else {
        CTrackerColors.light()
    }

    CompositionLocalProvider(
        LocalCTColors provides colors,
    ) {
        content()
    }
}


object CTrackerTheme {
    val colors: CTrackerColors
        @Composable
        @ReadOnlyComposable
        get() = LocalCTColors.current

    val typography: CTrackerTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalCTrackerTypography.current
}

