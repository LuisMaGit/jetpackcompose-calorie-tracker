package com.luisma.core.services.pagination_service

class PaginationService {

    fun shouldRequestOtherPage(
        state: PaginationState,
        index: Int
    ): PaginationState? {
        if (state.currentPage <= state.lastPage && index > state.lastIndex) {
            return state.copy(lastIndex = index)
        }
        return null
    }

    fun runPagination(
        lastPage: Int,
        lastIndex: Int,
        state: PaginationState
    ): PaginationState {
        var newState = state.copy(
            lastPage = lastPage,
            lastIndex = lastIndex
        )
        if (newState.lastPage > state.currentPage) {
            newState = newState.copy(
                nextPage = newState.nextPage++,
                currentPage = newState.currentPage++
            )
        }
        return newState
    }
}