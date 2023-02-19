package com.luisma.tracker_ui.pages.tracker_dashboard

import com.luisma.core.models.food.MealType
import com.luisma.core_ui.R


data class TrackerDashboardFoodCardResources(
    val title: Int,
    val image: Int,
    val buttonText: Int,
) {
    companion object {
        private val breakfast = TrackerDashboardFoodCardResources(
            title = R.string.breakfast,
            image = R.drawable.ic_breakfast,
            buttonText = R.string.add_breakfast
        )
        private val lunch = TrackerDashboardFoodCardResources(
            title = R.string.lunch,
            image = R.drawable.ic_lunch,
            buttonText = R.string.add_lunch
        )
        private val dinner = TrackerDashboardFoodCardResources(
            title = R.string.dinner,
            image = R.drawable.ic_dinner,
            buttonText = R.string.add_dinner
        )
        private val snack = TrackerDashboardFoodCardResources(
            title = R.string.snacks,
            image = R.drawable.ic_snack,
            buttonText = R.string.add_snack
        )

        private val foodByType = mapOf(
            MealType.Breakfast to breakfast,
            MealType.Lunch to lunch,
            MealType.Dinner to dinner,
            MealType.Snacks to snack,
        )

        fun getFoodResources(mealType: MealType): TrackerDashboardFoodCardResources {
            return foodByType[mealType]!!
        }
    }
}