package com.luisma.onboarding_ui.pages.onboarding_goal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisma.core.models.GoalUserProfile
import com.luisma.core.services.preferences_service.IUserPreferencesService
import com.luisma.core_ui.services.NavigationCommands
import com.luisma.core_ui.services.NavigationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingGoalVM @Inject constructor(
    private val navigationService: NavigationService,
    private val userProfileStoreService: IUserPreferencesService
) : ViewModel() {

    var state by mutableStateOf(OnboardingGoalModel())
        private set

    private fun tapGoal(goal: GoalUserProfile) {
        if (state.goal == goal) {
            return
        }
        state = state.copy(goal = goal)
    }

    private suspend fun goToNutrients() {
        userProfileStoreService.getUserProfile().collect { pref ->
            userProfileStoreService.setUserProfile(pref.copy(goal = state.goal))
            navigationService.setNavigationCommand(NavigationCommands.OnboardingNutrientsGoal)
        }
    }

    fun eventDispatcher(event: OnboardingGoalEvents) {
        when (event) {
            is OnboardingGoalEvents.TapNext -> viewModelScope.launch {
                goToNutrients()
            }
            is OnboardingGoalEvents.SetGoal -> tapGoal(event.goal)
        }
    }
}