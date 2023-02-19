package com.luisma.tracker_ui.pages.tracker_search_food

sealed class TrackerSearchFoodEvents {
    data class OnSubmitProduct(val productIndex: Int) : TrackerSearchFoodEvents()
    data class SetSearch(val search: String) : TrackerSearchFoodEvents()
    data class SetGramsConsumed(val gramsConsumed: String, val productIndex: Int) : TrackerSearchFoodEvents()
    data class ExpandProduct(val productIndex: Int) : TrackerSearchFoodEvents()
    data class PaginationTrigger(val productIndex: Int) : TrackerSearchFoodEvents()
    object SubmitSearch : TrackerSearchFoodEvents()
    object RetrySearch : TrackerSearchFoodEvents()
}
