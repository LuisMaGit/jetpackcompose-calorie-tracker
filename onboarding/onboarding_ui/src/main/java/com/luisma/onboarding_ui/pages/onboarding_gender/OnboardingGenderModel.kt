package com.luisma.onboarding_ui.pages.onboarding_gender

import com.luisma.core.models.GenderUserProfile

data class OnboardingGenderModel(
    val genderSelected: GenderUserProfile = GenderUserProfile.Female
)