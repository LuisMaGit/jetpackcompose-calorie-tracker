package com.luisma.onboarding_ui.pages.onboarding_welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisma.core_ui.services.NavigationCommands
import com.luisma.core_ui.services.NavigationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingWelcomeVM @Inject constructor(
    private val navigationService: NavigationService,
) : ViewModel() {

    private fun tapNext() {
        viewModelScope.launch {
            navigationService.setNavigationCommand(NavigationCommands.OnbaordingGender)
        }
    }

    fun eventDispatcher(event: OnboardingWelcomeEvents) {
        when (event) {
            OnboardingWelcomeEvents.TapNext -> tapNext()
        }
    }
}

