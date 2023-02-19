package com.luisma.tracker_ui.pages.tracker_dashboard.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.luisma.core.models.food.MealTrack
import com.luisma.core.models.food.MealType
import com.luisma.core_ui.components.CTPrimaryButton
import com.luisma.core_ui.theme.CTSpace
import com.luisma.tracker_ui.pages.tracker_dashboard.TrackerDashboardFoodCardResources


fun LazyListScope.foodTypesItems(
    mealType: MealType,
    meal: MealTrack,
    onTapDrop: () -> Unit,
    onTapAdd: () -> Unit,
    removeProduct: (index: Int) -> Unit,
    expanded: Boolean,
) {
    item {
        FoodTypeCard(
            modifier = Modifier.padding(
                start = CTSpace.pagePaddingHorizontal,
                end = CTSpace.pagePaddingHorizontal,
                bottom = CTSpace.space3
            ),
            mealType = mealType,
            kCal = meal.totalNutriments.energyKcal100g,
            carbs = meal.totalNutriments.carbohydrates100g,
            proteins = meal.totalNutriments.proteins100g,
            fats = meal.totalNutriments.fat100g,
            expanded = expanded,
            onTapDrop = onTapDrop,
        )
    }
    if (expanded) {
        val resource = TrackerDashboardFoodCardResources.getFoodResources(mealType)
        items(meal.productsFood.size) { index ->
            val isLast = meal.productsFood.size - 1 == index
            ProductCard(
                modifier = Modifier.padding(
                    start = CTSpace.pagePaddingHorizontal,
                    end = CTSpace.pagePaddingHorizontal,
                    top = CTSpace.space1,
                    bottom = if (isLast) {
                        0.dp
                    } else {
                        CTSpace.space1
                    }
                ),
                index = index,
                product = meal.productsFood[index],
                removeProduct = removeProduct
            )
        }
        item {
            CTPrimaryButton.Outlined(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = CTSpace.pagePaddingHorizontal,
                        end = CTSpace.pagePaddingHorizontal,
                        top = CTSpace.space1,
                        bottom = CTSpace.space4,
                    ),
                onClick = { onTapAdd() },
                text = stringResource(id = resource.buttonText),
                icon = Icons.Rounded.Add
            )
        }
    }
}
