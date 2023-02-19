package com.luisma.onboarding_ui.pages.onboarding_activity

import com.luisma.core.models.ActivityLevelUserProfile

sealed class OnboardingActivityEvents {
    object TapNext : OnboardingActivityEvents()
    class SetActivity(val activity: ActivityLevelUserProfile) : OnboardingActivityEvents()
}