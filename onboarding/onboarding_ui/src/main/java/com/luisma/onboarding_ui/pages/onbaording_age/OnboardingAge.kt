package com.luisma.onboarding_ui.pages.onbaording_age

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.luisma.core_ui.R
import com.luisma.onboarding_ui.components.Header
import com.luisma.onboarding_ui.components.Layout
import com.luisma.onboarding_ui.components.NumericTextField

@Composable
fun OnboardingAge(
    state: OnboardingAgeModel,
    eventDispatcher: (events: OnboardingAgeEvents) -> Unit
) {

    val keyboardFocus = LocalFocusManager.current
    val invalidAge = state.invalidAge()

    Layout(
        onClickNext = { eventDispatcher(OnboardingAgeEvents.TapNext) },
        enabledNextButton = !invalidAge
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Header(id = R.string.whats_your_age)
            NumericTextField(
                value = state.age.toString(),
                onValueChange = {
                    eventDispatcher(OnboardingAgeEvents.SetAge(it))
                },
                caption = stringResource(id = R.string.years),
                onSubmit = {
                    keyboardFocus.clearFocus()
                },
                errorText = stringResource(
                    id = R.string.error_age_cant_be_empty
                ),
                showError = invalidAge
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun OnboardingAgePreview() {
    OnboardingAge(
        state = OnboardingAgeModel(),
        eventDispatcher = {}
    )
}