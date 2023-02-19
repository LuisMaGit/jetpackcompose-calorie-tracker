package com.luisma.onboarding_ui.pages.onboarding_activity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisma.core.models.ActivityLevelUserProfile
import com.luisma.core.services.preferences_service.IUserPreferencesService
import com.luisma.core_ui.services.NavigationCommands
import com.luisma.core_ui.services.NavigationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingActivityVM @Inject constructor(
    private val navigationService: NavigationService,
    private val userProfileStoreService: IUserPreferencesService,
) : ViewModel() {

    var state by mutableStateOf(OnboardingActivityModel())
        private set

    private fun setActivity(activity: ActivityLevelUserProfile) {
        if (state.activity == activity) {
            return
        }

        state = state.copy(activity = activity)
    }

    private suspend fun goToGoal() {
        userProfileStoreService.getUserProfile().collect { pref ->
            userProfileStoreService.setUserProfile(pref.copy(activityLevel = state.activity))
            navigationService.setNavigationCommand(NavigationCommands.OnboardingGoal)
        }
    }

    fun eventDispatcher(event: OnboardingActivityEvents) {
        when (event) {
            is OnboardingActivityEvents.TapNext -> viewModelScope.launch {
                goToGoal()
            }
            is OnboardingActivityEvents.SetActivity -> setActivity(event.activity)
        }
    }

}