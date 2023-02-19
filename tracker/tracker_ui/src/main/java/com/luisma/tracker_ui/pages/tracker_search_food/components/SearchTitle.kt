package com.luisma.tracker_ui.pages.tracker_search_food.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.luisma.core.models.food.MealType
import com.luisma.core_ui.R
import com.luisma.core_ui.components.CTText

@Composable
fun SearchTitle(
    modifier: Modifier = Modifier,
    mealType: MealType
) {
    val mealText = when (mealType) {
        MealType.Breakfast -> stringResource(id = R.string.breakfast)
        MealType.Lunch -> stringResource(id = R.string.lunch)
        MealType.Dinner -> stringResource(id = R.string.dinner)
        MealType.Snacks -> stringResource(id = R.string.snacks)
    }

    CTText.H1(
        modifier = modifier,
        text = "${stringResource(id = R.string.add)} $mealText"
    )
}


@Preview(showBackground = true)
@Composable
fun SearchTitlePreview() {
    SearchTitle(mealType = MealType.Dinner)
}