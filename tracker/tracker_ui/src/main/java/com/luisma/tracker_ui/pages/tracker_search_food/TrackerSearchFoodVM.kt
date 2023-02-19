package com.luisma.tracker_ui.pages.tracker_search_food

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisma.core.MainDispatcher
import com.luisma.core.models.BaseResponse
import com.luisma.core.models.food.foodTypeDeserialize
import com.luisma.core.services.StringHelperService
import com.luisma.core.services.network_service.IFoodNetworkService
import com.luisma.core.services.pagination_service.PaginationService
import com.luisma.core.services.pagination_service.PaginationState
import com.luisma.core_ui.base.BaseUIState
import com.luisma.core_ui.services.GoBackNavigationCommand
import com.luisma.core_ui.services.NavigationCommands
import com.luisma.core_ui.services.NavigationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackerSearchFoodVM @Inject constructor(
    private val navigationService: NavigationService,
    private val foodNetworkService: IFoodNetworkService,
    private val stringHelperService: StringHelperService,
    private val initialState: TrackerSearchFoodModel,
    private val paginationService: PaginationService,
    savedStateHandle: SavedStateHandle,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
) : ViewModel() {

    var state by mutableStateOf(initialState)
        private set

    //region events
    init {
        val keyNavigation = savedStateHandle
            .get<String>(NavigationCommands.TrackerSearchFood.key)
        state = initialState.copy(
            mealType = foodTypeDeserialize(keyNavigation)
        )
    }

    fun eventDispatcher(events: TrackerSearchFoodEvents) {
        when (events) {
            is TrackerSearchFoodEvents.SetSearch -> setSearch(search = events.search)
            is TrackerSearchFoodEvents.SubmitSearch, TrackerSearchFoodEvents.RetrySearch -> {
                viewModelScope.launch(mainDispatcher) {
                    submitSearch()
                }
            }
            is TrackerSearchFoodEvents.PaginationTrigger -> {
                viewModelScope.launch(mainDispatcher) {
                    paginationTrigger(index = events.productIndex)
                }
            }
            is TrackerSearchFoodEvents.OnSubmitProduct -> onSubmitProduct(
                productIndex = events.productIndex
            )
            is TrackerSearchFoodEvents.SetGramsConsumed -> {
                setGramsConsumed(
                    productIndex = events.productIndex,
                    gramsConsumed = events.gramsConsumed
                )
            }
            is TrackerSearchFoodEvents.ExpandProduct -> {
                expandProduct(productIndex = events.productIndex)
            }
        }
    }
    //endregion events

    //region control
    private fun setSearch(search: String) {
        state = state.copy(search = search)
    }

    private fun setGramsConsumed(productIndex: Int, gramsConsumed: String) {
        val gramsConsumedByUser = stringHelperService.toInt(gramsConsumed)
        val product = state.products[productIndex]
            .copy(gramsConsumedByUser = gramsConsumedByUser)
        val mutableList = state.products.toMutableList()
        mutableList[productIndex] = product
        state = state.copy(products = mutableList)
    }

    private suspend fun submitSearch() {
        val search = state.search

        state = state.copy(
            uiState = BaseUIState.Loading,
            search = search,
            paginationState = PaginationState.initial(),
            products = emptyList(),
        )
        getFoodFromNetworkAndUpdateState()
    }

    private suspend fun paginationTrigger(index: Int) {
        val newPaginationState = paginationService.shouldRequestOtherPage(
            state = state.paginationState,
            index = index
        ) ?: return
        state = state.copy(paginationState = newPaginationState)
        getFoodFromNetworkAndUpdateState()
    }

    private suspend fun getFoodFromNetworkAndUpdateState() {
        val response = foodNetworkService.searchFood(
            search = state.search,
            page = state.paginationState.nextPage,
            pageSize = state.pageSize,
        )
        when (response) {
            is BaseResponse.Failure -> {
                state = state.copy(uiState = BaseUIState.Error)
            }
            is BaseResponse.Success -> {
                if (response.data.products.isEmpty()) {
                    state = state.copy(uiState = BaseUIState.Empty)
                    return
                }
                val newPaginationState = paginationService.runPagination(
                    state = state.paginationState,
                    lastIndex = response.data.products.lastIndex,
                    lastPage = response.data.count / state.pageSize
                )
                state = state.copy(
                    uiState = BaseUIState.Success,
                    products = state.products + response.data.products,
                    paginationState = newPaginationState
                )
            }
        }
    }

    private fun onSubmitProduct(productIndex: Int) {
        viewModelScope.launch(mainDispatcher) {
            navigationService.setNavigationCommand(NavigationCommands.GoBack)
            navigationService.setGoBackNavigationCommand(
                command = GoBackNavigationCommand.TrackerFromSearchToDashboard(
                    mealType = state.mealType,
                    food = state.products[productIndex]
                ),
            )
        }
    }

    private fun expandProduct(productIndex: Int) {
        val product = state.products[productIndex]
            .copy(
                expanded = !state.products[productIndex].expanded
            )
        val mutableList = state.products.toMutableList()
        mutableList[productIndex] = product
        state = state.copy(products = mutableList)
    }
    //endregion control
}