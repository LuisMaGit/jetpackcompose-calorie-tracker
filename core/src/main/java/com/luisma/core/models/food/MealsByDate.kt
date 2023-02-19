package com.luisma.core.models.food

import java.time.LocalDate

data class MealsByDate(
    val date: LocalDate,

    val caloriesGoal: Int,
    val carbsGoal: Int,
    val proteinGoal: Int,
    val fatGoal: Int,

    val caloriesTotal: Int,
    val carbsTotal: Int,
    val proteinTotal: Int,
    val fatTotal: Int,

    val caloriesPercentage: Int,
    val carbsPercentage: Int,
    val proteinPercentage: Int,
    val fatPercentage: Int,

    val meals: List<MealTrack>,
) {
    companion object {
        fun initial(): MealsByDate {
            return MealsByDate(
                date = LocalDate.MIN,
                caloriesGoal = 0,
                carbsGoal = 0,
                proteinGoal = 0,
                fatGoal = 0,
                caloriesTotal = 0,
                carbsTotal = 0,
                proteinTotal = 0,
                fatTotal = 0,
                caloriesPercentage = 0,
                carbsPercentage = 0,
                proteinPercentage = 0,
                fatPercentage = 0,
                meals = MealType.values()
                    .map { type ->
                        MealTrack(
                            mealType = type,
                            productsFood = emptyList()
                        )
                    },
            )
        }
    }
}
