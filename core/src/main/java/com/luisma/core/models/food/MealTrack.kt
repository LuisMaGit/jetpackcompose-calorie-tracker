package com.luisma.core.models.food

data class MealTrack(
    val mealType: MealType,
    val totalNutriments: NutrimentsFood = NutrimentsFood(),
    val totalGramsConsumed: Int = 0,
    val productsFood: List<ProductsFood>,
)