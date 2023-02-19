package com.luisma.core.services.pagination_service

data class PaginationState(
    var currentPage: Int,
    var nextPage: Int,
    var lastPage: Int,
    var lastIndex: Int,
) {
    companion object {
        fun initial(): PaginationState {
            return PaginationState(
                currentPage = 0,
                nextPage = 1,
                lastPage = 1,
                lastIndex = 1,
            )
        }
    }

    fun withLoading(): Boolean = lastPage != currentPage
}