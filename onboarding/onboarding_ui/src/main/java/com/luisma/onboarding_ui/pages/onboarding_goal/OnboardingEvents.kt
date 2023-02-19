package com.luisma.onboarding_ui.pages.onboarding_goal
import com.luisma.core.models.GoalUserProfile

sealed class OnboardingGoalEvents {
    object TapNext : OnboardingGoalEvents()
    class SetGoal(val goal: GoalUserProfile) : OnboardingGoalEvents()
}