package com.luisma.onboarding_ui.pages.onboarding_weight

sealed class OnboardingWeightEvents {
    object TapNext : OnboardingWeightEvents()
    class SetWeight(val weight: String) : OnboardingWeightEvents()
}
