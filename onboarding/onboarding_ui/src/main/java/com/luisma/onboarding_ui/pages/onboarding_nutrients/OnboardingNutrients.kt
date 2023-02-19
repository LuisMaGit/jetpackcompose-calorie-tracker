package com.luisma.onboarding_ui.pages.onboarding_nutrients

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luisma.core_ui.R
import com.luisma.core_ui.components.CTSpacerV
import com.luisma.core_ui.components.CTText
import com.luisma.core_ui.theme.CTrackerTheme
import com.luisma.onboarding_ui.components.Header
import com.luisma.onboarding_ui.components.Layout
import com.luisma.onboarding_ui.components.NumericTextField

@Composable
fun OnboardingNutrients(
    eventDispatcher: (event: OnboardingNutrientsEvents) -> Unit,
    state: OnboardingNutrientsModel,
) {
    Layout(
        onClickNext = { eventDispatcher(OnboardingNutrientsEvents.TapNext) },
        enabledNextButton = state.areAllFormsValid()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Header(id = R.string.what_are_your_nutrient_goals)
            if (!state.areAllFormsValid()) {
                CTText.Body1(
                    text = stringResource(id = R.string.error_invalid_values),
                    textStyle = CTrackerTheme.typography.body1.copy(color = Color.Red)
                )
            }
            CTSpacerV.Base2()
            LazyColumn {
                items(
                    NutrientsFormType.values().size
                ) { index ->
                    val type = NutrientsFormType.values()[index]
                    val value = state.formsMap[type]
                    NumericTextField(
                        modifier = Modifier.padding(bottom = 12.dp),
                        value = value.toString(),
                        onValueChange = { nutrient ->
                            eventDispatcher(
                                OnboardingNutrientsEvents.SetNutrients(
                                    typeNutrientForm = type,
                                    valueNutrient = nutrient,
                                )
                            )
                        },
                        caption = _FormCaption(type),
                    )
                }
            }
        }
    }
}


@Composable
private fun _FormCaption(type: NutrientsFormType): String {
    return when (type) {
        NutrientsFormType.Fat -> "% ${stringResource(id = R.string.fat)}"
        NutrientsFormType.Protein -> "% ${stringResource(id = R.string.protein)}"
        NutrientsFormType.Carb -> "% ${stringResource(id = R.string.carbs)}"
    }
}


@Preview(showBackground = true)
@Composable
fun OnboardingNutrientsPreview() {
    OnboardingNutrients(
        state = OnboardingNutrientsModel(),
        eventDispatcher = {}
    )
}