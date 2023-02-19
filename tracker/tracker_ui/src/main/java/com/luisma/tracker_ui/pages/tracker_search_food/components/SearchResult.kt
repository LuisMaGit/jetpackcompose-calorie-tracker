package com.luisma.tracker_ui.pages.tracker_search_food.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview
import com.luisma.core.models.food.NutrimentsFood
import com.luisma.core.models.food.ProductsFood
import com.luisma.core_ui.components.CTLoader
import com.luisma.core_ui.theme.CTSpace
import com.luisma.core_ui.theme.CTrackerTheme

@Composable
fun SearchResult(
    products: List<ProductsFood>,
    showLoadingTail: Boolean,
    expandProduct: (productIndex: Int) -> Unit,
    setGrams: (
        productIndex: Int,
        grams: String,
    ) -> Unit,
    paginationTrigger: (productIndex: Int) -> Unit,
    submit: (productIndex: Int) -> Unit,
) {

    fun listCount(): Int {
        return products.size + if (showLoadingTail) 1 else 0
    }

    val color = CTrackerTheme.colors

    LazyColumn {
        items(
            count = listCount()
        ) { index ->
            paginationTrigger(index)
            if (index == products.size) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(vertical = CTSpace.space2)
                        .fillMaxWidth()
                ) {
                    CTLoader()
                }
            } else {
                val product = products[index]
                SearchFoodCard(
                    modifier = Modifier
                        .drawBehind {
                            drawLine(
                                color = color.shade3,
                                start = Offset(0f, size.height),
                                end = Offset(size.width, size.height),
                                strokeWidth = 10f,
                            )
                        }
                        .padding(vertical = CTSpace.space3),
                    product = product,
                    onValueChange = { value ->
                        setGrams(index, value)
                    },
                    onSubmit = { submit(index) },
                    expandProduct = { expandProduct(index) }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SearchResultPreview() {
    SearchResult(
        products = List(5) { index ->
            ProductsFood(
                name = "Product $index",
                nutriments = NutrimentsFood(
                    proteins100g = index,
                    carbohydrates100g = index,
                    fat100g = index,
                    energyKcal100g = index,
                ),
                expanded = index % 2 == 0,
            )
        },
        showLoadingTail = true,
        setGrams = { _, _ -> },
        expandProduct = {},
        paginationTrigger = {},
        submit = {},
    )
}