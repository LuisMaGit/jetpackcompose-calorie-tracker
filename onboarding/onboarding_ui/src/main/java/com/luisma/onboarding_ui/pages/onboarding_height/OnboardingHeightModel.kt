package com.luisma.onboarding_ui.pages.onboarding_height

data class OnboardingHeightModel(val height: Int = 180) {
    fun invalidHeight(): Boolean = height == 0
}