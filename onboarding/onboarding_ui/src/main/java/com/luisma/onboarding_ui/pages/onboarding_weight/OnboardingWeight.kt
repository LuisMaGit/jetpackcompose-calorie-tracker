package com.luisma.onboarding_ui.pages.onboarding_weight

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.luisma.core_ui.R
import com.luisma.onboarding_ui.components.Header
import com.luisma.onboarding_ui.components.Layout
import com.luisma.onboarding_ui.components.NumericTextField

@Composable
fun OnboardingWeight(
    state: OnboardingWeightModel,
    eventDispatcher: (event: OnboardingWeightEvents) -> Unit
) {
    val invalidWeight = state.invalidWeight()
    Layout(
        onClickNext = { eventDispatcher(OnboardingWeightEvents.TapNext) },
        enabledNextButton = !invalidWeight
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Header(id = R.string.whats_your_weight)
            NumericTextField(
                value = state.weight.toString(),
                onValueChange = {
                    eventDispatcher(
                        OnboardingWeightEvents.SetWeight(it)
                    )
                },
                caption = stringResource(
                    id = R.string.kg
                ),
                errorText = stringResource(
                    R.string.error_weight_cant_be_empty
                ),
                showError = invalidWeight
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingWeightPreview() {
    OnboardingWeight(
        state = OnboardingWeightModel(),
        eventDispatcher = {}
    )
}