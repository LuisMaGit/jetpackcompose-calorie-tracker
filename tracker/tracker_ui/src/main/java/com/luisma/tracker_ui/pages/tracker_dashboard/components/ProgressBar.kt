package com.luisma.tracker_ui.pages.tracker_dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.luisma.core_ui.theme.CTrackerTheme

private enum class Nutriment {
    Carbs,
    Protein,
    Fat,
}

@Composable
fun ProgressBar(
    modifier: Modifier = Modifier,
    height: Dp = 30.dp,
    percentageCarbs: Float = 0f,
    percentageProtein: Float = 0f,
    percentageFlat: Float = 0f,
) {

    val mapOrder = mapOf(
        percentageCarbs to Nutriment.Carbs,
        percentageProtein to Nutriment.Protein,
        percentageFlat to Nutriment.Fat,
    )
    val mapColors = mapOf(
        Nutriment.Carbs to CTrackerTheme.colors.carbColor,
        Nutriment.Protein to CTrackerTheme.colors.proteinColor,
        Nutriment.Fat to CTrackerTheme.colors.fatColor,
    )

    val descendingOrder = mapOrder.keys.sortedDescending()
    val heightPxl = LocalDensity.current.run { height.toPx() }

    Box(
        modifier = modifier
            .clip(CircleShape)
            .height(height)
            .fillMaxWidth()
            .background(CTrackerTheme.colors.background)
            .drawWithContent {

                fun percentValidator(percentage: Float): Float {
                    if (percentage > 100f) {
                        return 1f
                    }
                    if (percentage < 0) {
                        return 0f
                    }

                    return percentage / 100
                }

                descendingOrder.forEach { nutrimentSorted ->
                    val nutriment = mapOrder[nutrimentSorted]
                    val width = size.width * percentValidator(nutrimentSorted)
                    drawRoundRect(
                        color = mapColors[nutriment]!!,
                        cornerRadius = CornerRadius(x = 50f, y = 50f),
                        size = Size(width, heightPxl)
                    )
                }
            }
    )
}


@Preview(showBackground = true)
@Composable
fun ProgressBarPreview() {
    Box(
        modifier = Modifier.background(Color.Green)
    ) {
        ProgressBar(
            percentageCarbs = 60F,
            percentageFlat = 50f,
            percentageProtein = 40F,
        )
    }
}