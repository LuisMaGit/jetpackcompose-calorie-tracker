package com.luisma.onboarding_ui.pages.onboarding_goal

import com.luisma.core.models.GoalUserProfile

data class OnboardingGoalModel(
    val goal: GoalUserProfile = GoalUserProfile.Keep
)