package com.luisma.core.models.food

data class Food(
    val count: Int = 0,
    val page: Int = 0,
    val pageCount: Int = 0,
    val pageSize: Int = 0,
    val products: List<ProductsFood> = emptyList(),
)

data class ProductsFood(
    val name: String = "",
    val imageUrl: String = "",
    val nutriments: NutrimentsFood = NutrimentsFood(),
    val gramsConsumedByUser: Int = 0,
    val expanded: Boolean = false,
) {
    fun kCalByGrams(): Int {
        return nutrimentsByGrams(nutriments.energyKcal100g)
    }

    fun carbsByGrams(): Int {
        return nutrimentsByGrams(nutriments.carbohydrates100g)
    }

    fun fatsByGrams(): Int {
        return nutrimentsByGrams(nutriments.fat100g)
    }

    fun proteinsByGrams(): Int {
        return nutrimentsByGrams(nutriments.proteins100g)
    }

    private fun nutrimentsByGrams(value: Int): Int {
        if (gramsConsumedByUser == 0) {
            return 0
        }
        return (value * gramsConsumedByUser) / 100
    }
}

data class NutrimentsFood(
    val carbohydrates100g: Int = 0,
    val energyKcal100g: Int = 0,
    val fat100g: Int = 0,
    val proteins100g: Int = 0,
)
