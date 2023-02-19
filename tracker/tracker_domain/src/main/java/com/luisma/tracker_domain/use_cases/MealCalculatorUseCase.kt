package com.luisma.tracker_domain.use_cases

import com.luisma.core.models.ActivityLevelUserProfile
import com.luisma.core.models.GenderUserProfile
import com.luisma.core.models.GoalUserProfile
import com.luisma.core.models.UserProfile
import com.luisma.core.models.food.MealTrack
import com.luisma.core.models.food.MealsByDate
import com.luisma.core.models.food.NutrimentsFood
import kotlin.math.roundToInt

class MealCalculatorUseCase {

    operator fun invoke(
        mealsByDateData: MealsByDate,
        userProfile: UserProfile
    ): MealsByDate {

        //totals by meal
        val newMeals = mutableListOf<MealTrack>()
        mealsByDateData.meals.forEach { meal ->
            val totalGramsConsumed100g = meal.productsFood.sumOf { it.gramsConsumedByUser }
            val energyKcal100g = meal.productsFood.sumOf { it.kCalByGrams() }
            val fat100g = meal.productsFood.sumOf { it.fatsByGrams() }
            val carbohydrates100g = meal.productsFood.sumOf { it.carbsByGrams() }
            val proteins100g = meal.productsFood.sumOf { it.proteinsByGrams() }

            val totalNutrimentsByMeal = NutrimentsFood(
                energyKcal100g = energyKcal100g,
                fat100g = fat100g,
                carbohydrates100g = carbohydrates100g,
                proteins100g = proteins100g
            )
            val mealWithTotals = meal.copy(
                totalNutriments = totalNutrimentsByMeal,
                totalGramsConsumed = totalGramsConsumed100g
            )
            newMeals.add(mealWithTotals)
        }

        //totals by date
        val carbsTotal = newMeals.sumOf {
            it.productsFood.sumOf { prod -> prod.carbsByGrams() }
        }
        val proteinTotal =
            newMeals.sumOf {
                it.productsFood.sumOf { prod -> prod.proteinsByGrams() }
            }
        val fatTotal = newMeals.sumOf {
            it.productsFood.sumOf { prod -> prod.fatsByGrams() }
        }
        val caloriesTotal = newMeals.sumOf {
            it.productsFood.sumOf { prod -> prod.carbsByGrams() }
        }

        //goals by date
        val caloriesGoal = dailyCalorieRequirement(userProfile)
        val carbsGoal = (caloriesGoal * userProfile.carbRatioFloat() / 4f).roundToInt()
        val proteinGoal = (caloriesGoal * userProfile.proteinRatioFloat() / 4f).roundToInt()
        val fatGoal = (caloriesGoal * userProfile.fatRatioFloat() / 9f).roundToInt()

        return mealsByDateData.copy(
            caloriesGoal = caloriesGoal,
            carbsGoal = carbsGoal,
            proteinGoal = proteinGoal,
            fatGoal = fatTotal,

            caloriesTotal = caloriesTotal,
            carbsTotal = carbsTotal,
            proteinTotal = proteinTotal,
            fatTotal = fatTotal,

            carbsPercentage = calculatePercentage(goal = carbsGoal, total = carbsTotal),
            fatPercentage = calculatePercentage(goal = fatGoal, total = fatTotal),
            proteinPercentage = calculatePercentage(goal = proteinGoal, total = proteinTotal),
            caloriesPercentage = calculatePercentage(goal = caloriesGoal, total = caloriesTotal),
            meals = newMeals
        )
    }

    private fun calculatePercentage(
        goal: Int,
        total: Int,
    ): Int {
        if (goal == 0) {
            return 0
        }
        return total * 100 / goal
    }

    private fun dailyCalorieRequirement(userProfile: UserProfile): Int {
        val activityFactor = when (userProfile.activityLevel) {
            ActivityLevelUserProfile.Low -> 1.2f
            ActivityLevelUserProfile.Medium -> 1.3f
            ActivityLevelUserProfile.High -> 1.4f
            else -> 0.0f
        }

        val calorieExtra = when (userProfile.goal) {
            GoalUserProfile.Lose -> -500
            GoalUserProfile.Keep -> 0
            GoalUserProfile.Gain -> 500
            else -> 0
        }
        return (bmr(userProfile) * activityFactor + calorieExtra).roundToInt()
    }

    private fun bmr(userProfile: UserProfile): Int {
        return when (userProfile.gender) {
            GenderUserProfile.Male -> {
                (66.47f + 13.75f * userProfile.weight +
                        5f * userProfile.height - 6.75f * userProfile.age).roundToInt()
            }
            GenderUserProfile.Female -> {
                (665.09f + 9.56f * userProfile.weight +
                        1.84f * userProfile.height - 4.67 * userProfile.age).roundToInt()
            }
            else -> 0
        }
    }


}