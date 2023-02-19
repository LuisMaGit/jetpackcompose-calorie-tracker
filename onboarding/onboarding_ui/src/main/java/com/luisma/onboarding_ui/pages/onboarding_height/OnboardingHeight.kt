package com.luisma.onboarding_ui.pages.onboarding_height

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
fun OnboardingHeight(
    state: OnboardingHeightModel,
    eventDispatcher: (event: OnboardingHeightEvent) -> Unit
) {

    val focus = LocalFocusManager.current

    Layout(
        onClickNext = { eventDispatcher(OnboardingHeightEvent.TapNext) },
        enabledNextButton = !state.invalidHeight()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Header(id = R.string.whats_your_height)
            NumericTextField(
                value = state.height.toString(),
                onValueChange = {
                    eventDispatcher(
                        OnboardingHeightEvent.SetHeight(it)
                    )
                },
                caption = stringResource(id = R.string.cm),
                showError = state.invalidHeight(),
                onSubmit = {
                    focus.clearFocus()
                },
                errorText = stringResource(id = R.string.error_height_cant_be_empty)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingHeightPreview() {
    OnboardingHeight(
        state = OnboardingHeightModel(),
        eventDispatcher = {}
    )
}