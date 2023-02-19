package com.luisma.tracker_ui.pages.tracker_dashboard.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luisma.core.models.food.MealType
import com.luisma.core_ui.R
import com.luisma.core_ui.components.CTText
import com.luisma.core_ui.theme.CTSpace
import com.luisma.tracker_ui.components.FoodCardWrapper
import com.luisma.tracker_ui.components.NutrimentsRow
import com.luisma.tracker_ui.pages.tracker_dashboard.TrackerDashboardFoodCardResources

@Composable
fun FoodTypeCard(
    modifier: Modifier = Modifier,
    mealType: MealType,
    kCal: Int,
    carbs: Int,
    proteins: Int,
    fats: Int,
    expanded: Boolean,
    onTapDrop: () -> Unit,
) {

    val resource = TrackerDashboardFoodCardResources.getFoodResources(mealType)
    Column(
        modifier = modifier,
    ) {
        FoodCardWrapper.WithVectorImage(
            modifier = Modifier.clickable { onTapDrop() },
            image = resource.image
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = CTSpace.space1)
            ) {
                HeaderAndDrop(
                    resource = resource,
                    expanded = expanded
                )
                KCalAndNutriments(
                    kCal = kCal,
                    carbs = carbs,
                    proteins = proteins,
                    fats = fats,
                )
            }
        }
    }
}


@Composable
private fun HeaderAndDrop(
    resource: TrackerDashboardFoodCardResources,
    expanded: Boolean,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = CTSpace.space1),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CTText.H3(
            text = stringResource(id = resource.title)
        )
        Icon(
            modifier = Modifier
                .size(30.dp),
            imageVector = if (expanded) {
                Icons.Rounded.KeyboardArrowUp
            } else {
                Icons.Rounded.KeyboardArrowDown
            },
            contentDescription = null
        )
    }
}


@Composable
private fun KCalAndNutriments(
    kCal: Int,
    carbs: Int,
    proteins: Int,
    fats: Int,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            CTText.H2(
                modifier = Modifier
                    .padding(end = CTSpace.space1)
                    .alignBy(LastBaseline),
                text = kCal.toString()
            )
            CTText.Body1(
                modifier = Modifier
                    .padding(end = CTSpace.space1)
                    .alignBy(LastBaseline),
                text = stringResource(id = R.string.kcal)
            )
        }
        NutrimentsRow(
            carbs = carbs,
            proteins = proteins,
            fats = fats,
        )
    }
}



@Preview(showBackground = true)
@Composable
fun FoodTypeCardPreview() {
    FoodTypeCard(
        mealType = MealType.Dinner,
        kCal = 0,
        fats = 0,
        carbs = 0,
        proteins = 0,
        expanded = true,
        onTapDrop = {},
    )

}




