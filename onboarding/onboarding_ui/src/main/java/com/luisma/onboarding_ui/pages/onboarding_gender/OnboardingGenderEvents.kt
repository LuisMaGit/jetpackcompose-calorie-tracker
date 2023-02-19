package com.luisma.onboarding_ui.pages.onboarding_gender

import com.luisma.core.models.GenderUserProfile


sealed class OnboardingGenderEvents {
    object TapNext : OnboardingGenderEvents()
    class TapGender(val gender: GenderUserProfile) : OnboardingGenderEvents()
}
