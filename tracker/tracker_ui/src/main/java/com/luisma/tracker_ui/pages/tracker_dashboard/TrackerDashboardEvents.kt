package com.luisma.tracker_ui.pages.tracker_dashboard

import com.luisma.core.models.food.MealType

sealed class TrackerDashboardEvents {
    class NextPreviousDay(val isNext: Boolean) : TrackerDashboardEvents()
    class AddFood(val mealType: MealType) : TrackerDashboardEvents()
    class DeleteFood(val mealType: MealType, val index: Int) : TrackerDashboardEvents()
    class ExpandFood(val mealType: MealType) : TrackerDashboardEvents()
}
