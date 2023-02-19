package com.luisma.onboarding_ui.pages.onbaording_age

sealed class OnboardingAgeEvents {
    object TapNext : OnboardingAgeEvents()
    class SetAge(val age: String) : OnboardingAgeEvents()
}