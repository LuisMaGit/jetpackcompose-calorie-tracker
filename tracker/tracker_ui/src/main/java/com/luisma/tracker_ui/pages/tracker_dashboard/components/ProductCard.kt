package com.luisma.tracker_ui.pages.tracker_dashboard.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.luisma.core.models.food.NutrimentsFood
import com.luisma.core.models.food.ProductsFood
import com.luisma.core_ui.R
import com.luisma.core_ui.components.CTText
import com.luisma.core_ui.theme.CTSpace
import com.luisma.tracker_ui.components.FoodCardWrapper
import com.luisma.tracker_ui.components.NutrimentsRow

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    index: Int,
    product: ProductsFood,
    removeProduct: (index: Int) -> Unit,
) {
    FoodCardWrapper.WithNetworkImage(
        modifier = modifier,
        url = product.imageUrl,
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = CTSpace.space1
            ),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            FirstColumn(
                modifier = Modifier.weight(.5f),
                name = product.name,
                grams = product.gramsConsumedByUser,
                kCal = product.nutriments.energyKcal100g
            )
            SecondColumn(
                modifier = Modifier.weight(.5f),
                carbs = product.nutriments.carbohydrates100g,
                proteins = product.nutriments.proteins100g,
                fats = product.nutriments.fat100g,
                onTapClose = { removeProduct(index) }
            )
        }
    }
}


@Composable
private fun FirstColumn(
    modifier: Modifier = Modifier,
    name: String,
    grams: Int,
    kCal: Int,
) {
    Column(
        modifier = modifier
    ) {
        CTText.H5(
            modifier = Modifier.padding(vertical = CTSpace.space1),
            text = name,
        )
        CTText.Body1(
            text = stringResource(
                id = R.string.kcal_100g,
                grams,
                kCal
            )
        )
    }
}

@Composable
private fun SecondColumn(
    modifier: Modifier = Modifier,
    carbs: Int,
    proteins: Int,
    fats: Int,
    onTapClose: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End
    ) {
        Icon(
            modifier = Modifier
                .clickable {
                    onTapClose()
                }
                .padding(vertical = CTSpace.space1),
            imageVector = Icons.Rounded.Close,
            contentDescription = ""
        )
        NutrimentsRow(
            carbs = carbs,
            proteins = proteins,
            fats = fats
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProductCardPreview() {
    ProductCard(
        index = 0,
        product = ProductsFood(
            name = "AAAAAAAAAAAAAA",
            imageUrl = "",
            nutriments = NutrimentsFood(
                carbohydrates100g = 10,
                energyKcal100g = 20,
                fat100g = 3,
                proteins100g = 1
            ),
            gramsConsumedByUser = 12
        ),
        removeProduct = {},
    )
}