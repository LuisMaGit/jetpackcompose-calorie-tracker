package com.luisma.core_data.services.database_service

import com.luisma.core.models.food.*
import com.luisma.core.services.JsonSerializationService
import foodtrackerdb.FoodTrackerTable
import kotlinx.serialization.Serializable
import java.time.LocalDate

data class FoodTrackerEntity(
    val day: Int?,
    val month: Int?,
    val year: Int?,
    val breakfast: List<ProductsFoodEntity>?,
    val lunch: List<ProductsFoodEntity>?,
    val dinner: List<ProductsFoodEntity>?,
    val snack: List<ProductsFoodEntity>?,
) {
    companion object {
        fun fromTable(
            jsonSerializationService: JsonSerializationService,
            table: FoodTrackerTable?,
        ): FoodTrackerEntity {
            val breakfast =
                if (table?.breakfast == null) null else jsonSerializationService.decodeFromString<List<ProductsFoodEntity>>(
                    string = table.breakfast
                )
            val lunch =
                if (table?.lunch == null) null else jsonSerializationService.decodeFromString<List<ProductsFoodEntity>>(
                    string = table.lunch
                )
            val dinner =
                if (table?.dinner == null) null else jsonSerializationService.decodeFromString<List<ProductsFoodEntity>>(
                    string = table.dinner
                )
            val snack =
                if (table?.snack == null) null else jsonSerializationService.decodeFromString<List<ProductsFoodEntity>>(
                    string = table.snack
                )

            return FoodTrackerEntity(
                day = table?.day?.toInt(),
                month = table?.month?.toInt(),
                year = table?.year?.toInt(),
                breakfast = breakfast,
                lunch = lunch,
                dinner = dinner,
                snack = snack,
            )
        }

        fun fromCore(mealsByDate: MealsByDate): FoodTrackerEntity {
            val breakfast =
                mealsByDate.meals.groupBy { it.mealType }[MealType.Breakfast]?.firstOrNull()
            val lunch = mealsByDate.meals.groupBy { it.mealType }[MealType.Lunch]?.firstOrNull()
            val dinner = mealsByDate.meals.groupBy { it.mealType }[MealType.Dinner]?.firstOrNull()
            val snack = mealsByDate.meals.groupBy { it.mealType }[MealType.Snacks]?.firstOrNull()
            return FoodTrackerEntity(
                day = mealsByDate.date.dayOfMonth,
                month = mealsByDate.date.monthValue,
                year = mealsByDate.date.year,
                breakfast = breakfast?.productsFood?.map {
                    ProductsFoodEntity.fromCore(it)
                },
                lunch = lunch?.productsFood?.map {
                    ProductsFoodEntity.fromCore(it)
                },
                dinner = dinner?.productsFood?.map {
                    ProductsFoodEntity.fromCore(it)
                },
                snack = snack?.productsFood?.map {
                    ProductsFoodEntity.fromCore(it)
                },
            )
        }
    }

    fun toTable(
        jsonSerializationService: JsonSerializationService,
    ): FoodTrackerTable {
        val breakfast =
            if (breakfast == null) null else jsonSerializationService.encodeToString(breakfast)
        val lunch = if (lunch == null) null else jsonSerializationService.encodeToString(lunch)
        val dinner =
            if (dinner == null) null else jsonSerializationService.encodeToString(dinner)
        val snack = if (snack == null) null else jsonSerializationService.encodeToString(snack)

        return FoodTrackerTable(
            day = day?.toLong() ?: 0,
            month = month?.toLong() ?: 0,
            year = year?.toLong() ?: 0,
            breakfast = breakfast,
            lunch = lunch,
            dinner = dinner,
            snack = snack,
        )
    }

    fun toCore(): MealsByDate {
        val base = MealsByDate.initial()
        val date =
            if (day == null || month == null || year == null) {
                base.date
            } else {
                LocalDate.of(
                    year,
                    month,
                    day,
                )
            }
        val breakfast =
            breakfast?.map {
                it.toCore()
            } ?: emptyList()
        val lunch =
            lunch?.map {
                it.toCore()
            } ?: emptyList()
        val dinner =
            dinner?.map {
                it.toCore()
            } ?: emptyList()
        val snack =
            snack?.map {
                it.toCore()
            } ?: emptyList()

        val meals = mutableListOf<MealTrack>()
        MealType.values().forEach { type ->
            when (type) {
                MealType.Breakfast -> meals += MealTrack(
                    mealType = type,
                    productsFood = breakfast,
                )
                MealType.Lunch -> meals += MealTrack(
                    mealType = type,
                    productsFood = lunch,
                )
                MealType.Dinner -> meals += MealTrack(
                    mealType = type,
                    productsFood = dinner
                )
                MealType.Snacks -> meals += MealTrack(
                    mealType = type,
                    productsFood = snack
                )
            }
        }

        return base.copy(
            date = date,
            meals = meals,
        )
    }


}

@Serializable
data class ProductsFoodEntity(
    val name: String?,
    val imageUrl: String?,
    val nutriments: NutrimentsFoodEntity?,
    val gramsConsumedByUser: Int?,
) {

    companion object {
        fun fromCore(product: ProductsFood): ProductsFoodEntity {
            return ProductsFoodEntity(
                name = product.name,
                imageUrl = product.imageUrl,
                nutriments = NutrimentsFoodEntity.fromCore(product.nutriments),
                gramsConsumedByUser = product.gramsConsumedByUser,
            )
        }
    }

    fun toCore(): ProductsFood {
        val base = ProductsFood()
        return ProductsFood(
            name = name ?: base.name,
            imageUrl = imageUrl ?: base.imageUrl,
            nutriments = nutriments?.toCore() ?: base.nutriments,
            gramsConsumedByUser = gramsConsumedByUser ?: base.gramsConsumedByUser,
        )
    }
}

@Serializable
data class NutrimentsFoodEntity(
    val carbohydrates100g: Int?,
    val energyKcal100g: Int?,
    val fat100g: Int?,
    val proteins100g: Int?,
) {

    companion object {
        fun fromCore(nutriment: NutrimentsFood): NutrimentsFoodEntity {
            return NutrimentsFoodEntity(
                carbohydrates100g = nutriment.carbohydrates100g,
                energyKcal100g = nutriment.energyKcal100g,
                fat100g = nutriment.fat100g,
                proteins100g = nutriment.proteins100g,
            )
        }
    }

    fun toCore(): NutrimentsFood {
        val base = NutrimentsFood()
        return NutrimentsFood(
            carbohydrates100g = carbohydrates100g ?: base.carbohydrates100g,
            energyKcal100g = energyKcal100g ?: base.energyKcal100g,
            fat100g = fat100g ?: base.fat100g,
            proteins100g = proteins100g ?: base.proteins100g,
        )
    }


}