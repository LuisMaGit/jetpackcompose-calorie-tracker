package com.luisma.tracker_ui.pages.tracker_dashboard.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.luisma.core_ui.R
import com.luisma.core_ui.components.CTSpacerH
import com.luisma.core_ui.components.CTSpacerV
import com.luisma.core_ui.components.CTText
import com.luisma.core_ui.theme.CTSpace
import com.luisma.core_ui.theme.CTrackerTheme


@Composable
fun DashboardGraphs(
    modifier: Modifier = Modifier,
    durationAnimations: Int = 500,
    carbs: Int,
    carbsPercentage: Float,
    proteins: Int,
    proteinsPercentage: Float,
    fats: Int,
    fatsPercentage: Float,
    currentKCal: Int,
    goalKCal: Int,
) {

    val animationCurrentKCal by animateIntAsState(
        targetValue = currentKCal,
        animationSpec = tween(durationMillis = durationAnimations)
    )

    val animationGoalKCal by animateIntAsState(
        targetValue = goalKCal,
        animationSpec = tween(durationMillis = durationAnimations)
    )

    val animationCarbsPercentage by animateFloatAsState(
        targetValue = carbsPercentage,
        animationSpec = tween(durationMillis = durationAnimations)
    )

    val animationProteinsPercentage by animateFloatAsState(
        targetValue = proteinsPercentage,
        animationSpec = tween(durationMillis = durationAnimations)
    )

    val animationFatsPercentage by animateFloatAsState(
        targetValue = fatsPercentage,
        animationSpec = tween(durationMillis = 500)
    )

    Wrapper(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = CTSpace.pagePaddingHorizontal,
                vertical = 40.dp
            )
        ) {
            KCalIndicator(
                currentKCal = animationCurrentKCal,
                goalKCal = animationGoalKCal
            )
            ProgressBar(
                modifier = Modifier
                    .padding(
                        top = CTSpace.space1,
                        bottom = CTSpace.space5
                    ),
                percentageCarbs = animationCarbsPercentage,
                percentageProtein = animationProteinsPercentage,
                percentageFlat = animationFatsPercentage,
            )
            CircularProgressGraphs(
                carbs = carbs,
                carbsPercentage = animationCarbsPercentage,
                proteins = proteins,
                proteinsPercentage = animationProteinsPercentage,
                fats = fats,
                fatsPercentage = animationFatsPercentage,
            )
        }
    }
}


@Composable
private fun Wrapper(
    modifier: Modifier = Modifier,
    backgroundColor: Color = CTrackerTheme.colors.brightGreen,
    bottomRadius: Float = 100f,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(
                RoundedCornerShape(
                    bottomEnd = bottomRadius,
                    bottomStart = bottomRadius,
                )
            )
            .background(color = backgroundColor)
    ) {
        CTSpacerV.Base4()
        content()
    }
}


@Composable
private fun KCalIndicator(
    currentKCal: Int,
    goalKCal: Int,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
    ) {
        KCalCounter(kcal = currentKCal)
        Column {
            CTText.Body1(
                text = stringResource(id = R.string.your_goal),
                textStyle = CTrackerTheme.typography.body1.copy(CTrackerTheme.colors.background),
            )
            KCalCounter(kcal = goalKCal)
        }
    }
}

@Composable
private fun KCalCounter(
    kcal: Int,
    color: Color = CTrackerTheme.colors.background,
) {
    Row(
        verticalAlignment = Alignment.Bottom
    ) {
        CTText.H2(
            text = kcal.toString(),
            textStyle = CTrackerTheme.typography.h2.copy(color),
            modifier = Modifier
                .alignBy(LastBaseline),
        )
        CTSpacerH.Base()
        CTText.Body1(
            text = stringResource(id = R.string.kcal),
            textStyle = CTrackerTheme.typography.body1.copy(color),
            modifier = Modifier
                .alignBy(LastBaseline),
        )
    }
}

@Composable
private fun CircularProgressGraphs(
    carbs: Int,
    carbsPercentage: Float,
    proteins: Int,
    proteinsPercentage: Float,
    fats: Int,
    fatsPercentage: Float,
    spaceBetween: Dp = 40.dp,
) {
    Row {
        CircularProgress(
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1f),
            grams = carbs,
            percentage = carbsPercentage,
            accentColor = CTrackerTheme.colors.carbColor,
            name = stringResource(id = R.string.carbs)
        )
        CTSpacerH.Custom(value = spaceBetween)
        CircularProgress(
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1f),
            grams = proteins,
            percentage = proteinsPercentage,
            accentColor = CTrackerTheme.colors.proteinColor,
            name = stringResource(id = R.string.protein)
        )
        CTSpacerH.Custom(value = spaceBetween)
        CircularProgress(
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1f),
            grams = fats,
            percentage = fatsPercentage,
            accentColor = CTrackerTheme.colors.fatColor,
            name = stringResource(id = R.string.fat)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardGraphsPreview() {
    DashboardGraphs(
        carbs = 1,
        carbsPercentage = 10f,
        proteins = 1,
        proteinsPercentage = 15f,
        fats = 1,
        fatsPercentage = 102f,
        currentKCal = 1,
        goalKCal = 52525,
    )
}

