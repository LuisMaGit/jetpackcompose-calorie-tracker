package com.luisma.onboarding_ui.pages.onboarding_height

sealed class OnboardingHeightEvent {
    object TapNext : OnboardingHeightEvent()
    class SetHeight(val height: String) : OnboardingHeightEvent()
}
