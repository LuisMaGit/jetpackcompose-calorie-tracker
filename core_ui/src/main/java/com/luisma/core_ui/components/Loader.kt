package com.luisma.core_ui.components

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.luisma.core_ui.theme.CTrackerTheme
import com.luisma.core_ui.theme.CalorieTrackerTheme

@Composable
fun CTLoader(
    modifier: Modifier = Modifier,
    color: Color = CTrackerTheme.colors.brightGreen,
    strokeWidth: Dp = ProgressIndicatorDefaults.StrokeWidth
) {
    CircularProgressIndicator(
        modifier = modifier,
        color = color,
        strokeWidth = strokeWidth
    )
}

@Preview
@Composable
fun CTLoaderPreview() {
    CalorieTrackerTheme {
        CTLoader()
    }
}