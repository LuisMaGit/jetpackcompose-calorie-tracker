package com.luisma.core_data.services.database_service

import com.luisma.core.models.food.MealsByDate
import com.luisma.core.services.JsonSerializationService
import com.luisma.core.services.database_service.IFoodDataBaseService
import foodtrackerdb.FoodTrackerTableQueries
import java.time.LocalDate

class FoodDataBaseService(
    private val queries: FoodTrackerTableQueries,
    private val jsonSerializationService: JsonSerializationService,
) : IFoodDataBaseService {

    override suspend fun getFoodFrom(date: LocalDate): MealsByDate {
        val table = queries.getFoodByDate(
            day = date.dayOfMonth.toLong(),
            month = date.month.value.toLong(),
            year = date.year.toLong()
        ).executeAsList().firstOrNull()


        val entity = FoodTrackerEntity.fromTable(
            jsonSerializationService = jsonSerializationService,
            table = table
        )

        return entity.toCore()
    }

    override suspend fun setFood(food: MealsByDate) {
        val entity = FoodTrackerEntity.fromCore(food)
        queries.setFood(entity.toTable(jsonSerializationService))
    }

}