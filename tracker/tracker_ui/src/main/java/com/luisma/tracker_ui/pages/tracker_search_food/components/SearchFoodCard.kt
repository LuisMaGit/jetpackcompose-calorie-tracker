package com.luisma.tracker_ui.pages.tracker_search_food.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luisma.core.models.food.ProductsFood
import com.luisma.core_ui.R
import com.luisma.core_ui.components.CTText
import com.luisma.core_ui.theme.CTSpace
import com.luisma.core_ui.theme.CTrackerTheme
import com.luisma.tracker_ui.components.FoodCardWrapper
import com.luisma.tracker_ui.components.NutrimentsRow

@Composable
fun SearchFoodCard(
    modifier: Modifier = Modifier,
    product: ProductsFood,
    onValueChange: (value: String) -> Unit,
    onSubmit: () -> Unit,
    expandProduct: () -> Unit,
) {
    val color = CTrackerTheme.colors
    val gramsConsumed = if (product.gramsConsumedByUser == 0) {
        ""
    } else {
        product.gramsConsumedByUser.toString()
    }

    val gramsValid = gramsConsumed.isNotBlank()

    fun submit() {
        if (!gramsValid) {
            return
        }

        onSubmit()
    }

    Column(
        modifier = modifier
    ) {

        FoodCardWrapper.WithNetworkImage(
            modifier = Modifier.clickable { expandProduct() },
            url = product.imageUrl,
        ) {
            Row(
                modifier = Modifier.padding(
                    horizontal = CTSpace.space1
                ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProductNameAndKCal(
                    modifier = Modifier.weight(.5f),
                    name = product.name,
                    kCal = product.nutriments.energyKcal100g
                )
                NutrimentsRow(
                    carbs = product.nutriments.carbohydrates100g,
                    proteins = product.nutriments.proteins100g,
                    fats = product.nutriments.fat100g
                )
            }
        }
        if (product.expanded) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = CTSpace.space3,
                        bottom = CTSpace.space3,
                        end = CTSpace.space3,
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                GramsForm(
                    value = gramsConsumed,
                    onValueChange = onValueChange,
                    onEditingComplete = { submit() }
                )
                Icon(
                    modifier = Modifier.clickable { submit() },
                    imageVector = Icons.Filled.Check,
                    contentDescription = "",
                    tint = if (gramsValid) color.inverseBackground else color.shade3
                )
            }
        }
    }
}

@Composable
private fun ProductNameAndKCal(
    modifier: Modifier = Modifier,
    name: String,
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
                id = R.string.kcal_per_100g,
                kCal
            )
        )
    }
}

@Composable
private fun GramsForm(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (value: String) -> Unit,
    onEditingComplete: () -> Unit
) {
    val color = CTrackerTheme.colors
    Row(
        modifier = modifier
            .border(
                width = 1.dp,
                brush = SolidColor(color.inverseBackground),
                shape = RoundedCornerShape(CTSpace.space1)
            )
            .padding(
                horizontal = CTSpace.space2,
                vertical = CTSpace.space3
            ),
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onEditingComplete()
                }
            )
        )
        CTSpace.space1
        CTText.Body1(
            text = stringResource(id = R.string.grams)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun SearchFoodCardPreview() {
    SearchFoodCard(
        product = ProductsFood(
            name = "Product",
            expanded = true,
        ),
        expandProduct = {},
        onSubmit = {},
        onValueChange = {}
    )
}