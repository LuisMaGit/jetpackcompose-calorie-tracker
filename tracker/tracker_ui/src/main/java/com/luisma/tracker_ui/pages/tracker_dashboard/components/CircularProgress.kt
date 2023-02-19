package com.luisma.tracker_ui.pages.tracker_dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luisma.core_ui.components.CTText
import com.luisma.core_ui.theme.CTrackerTheme

@Composable
fun CircularProgress(
    modifier: Modifier = Modifier,
    strokeWidth: Float = 24f,
    backgroundColor: Color = CTrackerTheme.colors.background,
    accentColor: Color = CTrackerTheme.colors.brightGreen,
    percentage: Float = 0f,
    grams: Int = 0,
    name: String = "Carbs",
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .drawBehind {
                drawArc(
                    brush = SolidColor(backgroundColor),
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(
                        width = strokeWidth,
                    )
                )
            }
            .drawBehind {
                drawArc(
                    brush = SolidColor(accentColor),
                    startAngle = 90f,
                    sweepAngle = 3.6f * percentage,
                    useCenter = false,
                    style = Stroke(
                        width = strokeWidth,
                        cap = StrokeCap.Round
                    )
                )
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                CTText.H2(
                    text = grams.toString(),
                    maxLines = 1,
                    textStyle = CTrackerTheme.typography.h2.copy(
                        color = backgroundColor,
                    ),
                    modifier = Modifier
                        .alignBy(LastBaseline),
                )
                CTText.Body1(
                    text = " g",
                    maxLines = 1,
                    textStyle = CTrackerTheme.typography.body1.copy(
                        color = backgroundColor,
                    ),
                    modifier = Modifier
                        .alignBy(LastBaseline),
                )
            }
            CTText.Body1(
                text = name,
                maxLines = 1,
                textStyle = CTrackerTheme.typography.body1.copy(
                    color = backgroundColor,
                    fontSize = 14.sp
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CircularProgressPreview() {
    Box(
        modifier = Modifier
            .background(color = Color.Black)
    ) {
        CircularProgress(
            modifier = Modifier.size(200.dp),
            percentage = 15F
        )
    }
}