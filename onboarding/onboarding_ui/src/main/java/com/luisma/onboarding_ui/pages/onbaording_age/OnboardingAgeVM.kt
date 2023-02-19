package com.luisma.onboarding_ui.pages.onbaording_age

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
class OnboardingAgeVM @Inject constructor(
    private val navigationService: NavigationService,
    private val userPrefService: IUserPreferencesService,
    private val stringHelperService: StringHelperService,
) : ViewModel() {

    var state by mutableStateOf(OnboardingAgeModel())
        private set

    private fun setAge(value: String) {
        state = state.copy(age = stringHelperService.toInt(value))
    }

    private suspend fun goToHeight() {
        userPrefService.getUserProfile().collect { user ->
            userPrefService.setUserProfile(
                user.copy(age = state.age)
            )
            navigationService.setNavigationCommand(
                NavigationCommands.OnbaordingHeight
            )
        }
    }

    fun eventDispatcher(event: OnboardingAgeEvents) {
        when (event) {
            is OnboardingAgeEvents.TapNext -> viewModelScope.launch {
                goToHeight()
            }
            is OnboardingAgeEvents.SetAge -> setAge(event.age)
        }
    }
}