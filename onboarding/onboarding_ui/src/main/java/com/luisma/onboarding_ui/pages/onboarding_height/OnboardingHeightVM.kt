package com.luisma.onboarding_ui.pages.onboarding_height

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
class OnboardingHeightVM @Inject constructor(
    private val navigationService: NavigationService,
    private val userProfileStoreService: IUserPreferencesService,
    private val stringHelperService: StringHelperService,
) : ViewModel() {

    var state by mutableStateOf(OnboardingHeightModel())
        private set

    private fun setHeight(height: String) {
        state = state.copy(
            height = stringHelperService.toInt(height)
        )
    }

    private suspend fun goToWeight() {
        userProfileStoreService.getUserProfile().collect { pref ->
            userProfileStoreService.setUserProfile(
                pref.copy(height = state.height)
            )
            navigationService.setNavigationCommand(
                NavigationCommands.OnboardingWeight
            )
        }
    }

    fun eventDispatcher(event: OnboardingHeightEvent) {
        when (event) {
            is OnboardingHeightEvent.TapNext -> viewModelScope.launch {
                goToWeight()
            }
            is OnboardingHeightEvent.SetHeight -> setHeight(event.height)
        }
    }
}