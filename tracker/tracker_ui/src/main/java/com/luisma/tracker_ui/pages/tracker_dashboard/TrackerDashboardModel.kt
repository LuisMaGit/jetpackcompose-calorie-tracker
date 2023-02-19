package com.luisma.tracker_ui.pages.tracker_dashboard

import com.luisma.core.models.food.MealType
import com.luisma.core.models.food.MealsByDate
import java.time.LocalDate

data class TrackerDashboardModel(
    val currentFood: MealsByDate,
    val currentDate: LocalDate,
    val expandedMeal: Map<MealType, Boolean>
) {

    companion object {
        fun initial(): TrackerDashboardModel {
            val mealInitial = MealsByDate.initial()
            return TrackerDashboardModel(
                currentFood = mealInitial,
                currentDate = LocalDate.now(),
                expandedMeal = MealType.values().associateWith { false }
            )
        }
    }

    fun mealTypes(): Array<MealType> = MealType.values()
}

