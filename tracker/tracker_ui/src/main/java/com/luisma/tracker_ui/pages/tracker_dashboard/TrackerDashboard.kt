package com.luisma.tracker_ui.pages.tracker_dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.luisma.core.models.food.MealsByDate
import com.luisma.core_ui.theme.CTSpace
import com.luisma.tracker_ui.pages.tracker_dashboard.components.DashboardGraphs
import com.luisma.tracker_ui.pages.tracker_dashboard.components.DatePicker
import com.luisma.tracker_ui.pages.tracker_dashboard.components.foodTypesItems

@Composable
fun TrackerDashboard(
    eventDispatcher: (event: TrackerDashboardEvents) -> Unit,
    state: TrackerDashboardModel,
) {
    LazyColumn {
        item {
            DashboardGraphs(
                carbsPercentage = state.currentFood.carbsPercentage.toFloat(),
                proteinsPercentage = state.currentFood.proteinPercentage.toFloat(),
                fatsPercentage = state.currentFood.fatPercentage.toFloat(),
                carbs = state.currentFood.carbsTotal,
                proteins = state.currentFood.proteinTotal,
                fats = state.currentFood.fatTotal,
                currentKCal = state.currentFood.caloriesTotal,
                goalKCal = state.currentFood.caloriesGoal,
            )
        }
        item {
            DatePicker(
                modifier = Modifier.padding(
                    vertical = CTSpace.space3,
                    horizontal = CTSpace.pagePaddingHorizontal
                ),
                tapLeft = {
                    eventDispatcher(TrackerDashboardEvents.NextPreviousDay(isNext = false))
                },
                tapRight = {
                    eventDispatcher(TrackerDashboardEvents.NextPreviousDay(isNext = true))
                },
                date = state.currentDate
            )
        }
        state.mealTypes().map { mealType ->
            foodTypesItems(
                mealType = mealType,
                meal = state.currentFood.meals[mealType.ordinal],
                onTapDrop = { eventDispatcher(TrackerDashboardEvents.ExpandFood(mealType)) },
                onTapAdd = { eventDispatcher(TrackerDashboardEvents.AddFood(mealType)) },
                removeProduct = { index ->
                    eventDispatcher(
                        TrackerDashboardEvents.DeleteFood(
                            mealType = mealType,
                            index = index
                        )
                    )
                },
                expanded = state.expandedMeal[mealType]!!
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TrackerDashboardPreview() {
    TrackerDashboard(
        eventDispatcher = {},
        state = TrackerDashboardModel.initial().copy(
            currentFood = MealsByDate.initial().copy(
                fatPercentage = 10,
                proteinPercentage = 40,
                carbsPercentage = 70,
            )
        )
    )
}