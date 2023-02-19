package com.luisma.core.models.food

enum class MealType {
    Breakfast,
    Lunch,
    Dinner,
    Snacks;
}

fun foodTypeSerialize(type: MealType): String {
    return type.name
}

fun foodTypeDeserialize(string: String?): MealType {
    if (string == null) {
        return MealType.Breakfast
    }
    return try {
        MealType.valueOf(string)
    } catch (e: Exception) {
        return MealType.Breakfast
    }
}