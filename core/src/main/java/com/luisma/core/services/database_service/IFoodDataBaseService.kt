package com.luisma.core.services.database_service

import com.luisma.core.models.food.MealsByDate
import java.time.LocalDate

interface IFoodDataBaseService {
    suspend fun getFoodFrom(date: LocalDate): MealsByDate
    suspend fun setFood(food: MealsByDate)
}