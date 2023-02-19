package com.luisma.tracker_ui.pages.tracker_search_food

import com.luisma.core.services.pagination_service.PaginationState
import com.luisma.core.models.food.MealType
import com.luisma.core.models.food.ProductsFood
import com.luisma.core_ui.base.BaseUIState

data class TrackerSearchFoodModel(
    val products: List<ProductsFood>,
    val search: String,
    val pageSize: Int,
    val uiState: BaseUIState,
    val mealType: MealType,
    val paginationState: PaginationState
) {
    companion object {
        fun initial(): TrackerSearchFoodModel {
            return TrackerSearchFoodModel(
                products = emptyList(),
                search = "",
                pageSize = 10,
                uiState = BaseUIState.Initial,
                mealType = MealType.Breakfast,
                paginationState = PaginationState.initial()
            )
        }
    }
}