package com.luisma.onboarding_ui.pages.onboarding_weight

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisma.core.services.StringHelperService
import com.luisma.core.services.preferences_service.IUserPreferencesService
import com.luisma.core_ui.services.NavigationCommands
import com.luisma.core_ui.services.NavigationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingWeightVM @Inject constructor(
    private val navigationService: NavigationService,
    private val userProfileStoreService: IUserPreferencesService,
    private val stringHelperService: StringHelperService,
) : ViewModel() {


    var state by mutableStateOf(OnboardingWeightModel())
        private set

    private fun setWeight(value: String) {
        state = state.copy(
            weight = stringHelperService.toFloat(value)
        )
    }

    private suspend fun goToActivityLevel() {
        userProfileStoreService.getUserProfile().collect { pref ->
            userProfileStoreService.setUserProfile(pref.copy(weight = state.weight))
            navigationService.setNavigationCommand(
                NavigationCommands.OnboardingActivityLevel
            )
        }
    }

    fun eventDispatcher(event: OnboardingWeightEvents) {
        when (event) {
            is OnboardingWeightEvents.TapNext -> viewModelScope.launch {
                goToActivityLevel()
            }
            is OnboardingWeightEvents.SetWeight -> setWeight(event.weight)
        }
    }

}