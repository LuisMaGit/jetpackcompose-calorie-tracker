package com.luisma.onboarding_ui.pages.onboarding_gender

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.luisma.core.models.GenderUserProfile
import com.luisma.core_ui.R
import com.luisma.core_ui.components.CTSpacerH
import com.luisma.core_ui.components.CTSpacerV
import com.luisma.onboarding_ui.components.Header
import com.luisma.onboarding_ui.components.Layout
import com.luisma.onboarding_ui.components.ToggleButton

@Composable
fun OnboardingGender(
    eventDispatcher: (event: OnboardingGenderEvents) -> Unit,
    state: OnboardingGenderModel,
) {
    Layout(onClickNext = {
        eventDispatcher(OnboardingGenderEvents.TapNext)
    }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Header(id = R.string.whats_your_gender)
            CTSpacerV.Base2()
            GenderButtons(
                eventDispatcher = eventDispatcher,
                state = state,
            )
        }
    }
}

@Composable
private fun GenderButtons(
    eventDispatcher: (event: OnboardingGenderEvents) -> Unit,
    state: OnboardingGenderModel,
) {
    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        ToggleButton(
            active = state.genderSelected == GenderUserProfile.Female,
            text = stringResource(id = R.string.female),
            onClick = {
                eventDispatcher(
                    OnboardingGenderEvents.TapGender(GenderUserProfile.Female)
                )
            }
        )
        CTSpacerH.Base2()
        ToggleButton(
            active = state.genderSelected == GenderUserProfile.Male,
            text = stringResource(id = R.string.male),
            onClick = {
                eventDispatcher(
                    OnboardingGenderEvents.TapGender(GenderUserProfile.Male)
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingGenderPreview() {
    OnboardingGender(
        eventDispatcher = {},
        state = OnboardingGenderModel(
            genderSelected = GenderUserProfile.Male
        )
    )
}