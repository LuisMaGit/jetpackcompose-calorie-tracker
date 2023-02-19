package com.luisma.onboarding_ui.pages.onboarding_activity

import com.luisma.core.models.ActivityLevelUserProfile

data class OnboardingActivityModel(
    val activity: ActivityLevelUserProfile = ActivityLevelUserProfile.Medium
)