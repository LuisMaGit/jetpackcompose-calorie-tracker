package com.luisma.onboarding_ui.pages.onboarding_weight

data class OnboardingWeightModel(val weight: Float = 80.0f) {
    fun invalidWeight(): Boolean = weight == 0.0f
}