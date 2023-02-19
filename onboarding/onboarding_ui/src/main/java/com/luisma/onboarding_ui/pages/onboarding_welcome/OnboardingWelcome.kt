package com.luisma.onboarding_ui.pages.onboarding_welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luisma.core_ui.R
import com.luisma.core_ui.components.CTSpacerV
import com.luisma.core_ui.theme.CalorieTrackerTheme
import com.luisma.onboarding_ui.components.Header
import com.luisma.onboarding_ui.components.NextButton

@Composable
fun OnboardingWelcome(
    eventDispatcher: (event: OnboardingWelcomeEvents) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Header(id = R.string.welcome_text)
        CTSpacerV.Base2()
        NextButton(
            onClick = { eventDispatcher(OnboardingWelcomeEvents.TapNext) },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingWelcomePreview() {
    CalorieTrackerTheme {
        OnboardingWelcome(
            eventDispatcher = {}
        )
    }
}