package com.luisma.tracker_ui.pages.tracker_search_food

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import com.luisma.core.models.food.ProductsFood
import com.luisma.core_ui.base.BaseUIState
import com.luisma.core_ui.theme.CTSpace
import com.luisma.tracker_ui.pages.tracker_search_food.components.*

@Composable
fun TrackerSearchFood(
    state: TrackerSearchFoodModel,
    eventDispatcher: (event: TrackerSearchFoodEvents) -> Unit,
) {
    val spaceFromSearchModifier = Modifier.padding(top = CTSpace.space3)
    val focusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = true) {
        focusRequester.requestFocus()
    }


    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = CTSpace.space2)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        focusManager.clearFocus()
                    }
                )
            }
    ) {
        SearchTitle(
            modifier = Modifier
                .padding(vertical = CTSpace.space3),
            mealType = state.mealType
        )
        SearchForm(
            value = state.search,
            onValueChange = {
                eventDispatcher(
                    TrackerSearchFoodEvents.SetSearch(search = it)
                )
            },
            enabled = state.uiState != BaseUIState.Loading,
            focusRequester = focusRequester,
            onSubmit = {
                focusManager.clearFocus()
                eventDispatcher(
                    TrackerSearchFoodEvents.SubmitSearch
                )
            }
        )
        when (state.uiState) {
            BaseUIState.Loading -> SearchLoading(
                modifier = spaceFromSearchModifier
            )
            BaseUIState.Error -> SearchError(
                modifier = spaceFromSearchModifier
            )
            BaseUIState.Empty -> SearchEmpty(
                modifier = spaceFromSearchModifier
            )
            BaseUIState.Success -> SearchResult(
                products = state.products,
                showLoadingTail = state.paginationState.withLoading(),
                expandProduct = { index ->
                    eventDispatcher(
                        TrackerSearchFoodEvents.ExpandProduct(index)
                    )
                },
                setGrams = { index, grams ->
                    eventDispatcher(
                        TrackerSearchFoodEvents.SetGramsConsumed(
                            productIndex = index,
                            gramsConsumed = grams,
                        )
                    )
                },
                paginationTrigger = { index ->
                    eventDispatcher(
                        TrackerSearchFoodEvents.PaginationTrigger(
                            productIndex = index,
                        )
                    )
                },
                submit = { index ->
                    eventDispatcher(
                        TrackerSearchFoodEvents.OnSubmitProduct(index)
                    )
                }
            )
            else -> Unit
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TrackerSearchFoodPreview() {
    TrackerSearchFood(
        state = TrackerSearchFoodModel.initial().copy(
            uiState = BaseUIState.Success,
            products = List(4) { index ->
                ProductsFood(
                    name = "Product $index",
                    expanded = index % 2 == 0,
                    gramsConsumedByUser = index
                )
            }
        ),
        eventDispatcher = {}
    )
}