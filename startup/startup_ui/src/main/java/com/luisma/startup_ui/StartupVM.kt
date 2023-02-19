package com.luisma.startup_ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisma.core.services.preferences_service.IUserPreferencesService
import com.luisma.core_ui.services.NavigationCommands
import com.luisma.core_ui.services.NavigationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StartupVM @Inject constructor(
    userPrefService: IUserPreferencesService,
    navigationService: NavigationService
) : ViewModel() {

    init {
        viewModelScope.launch {
            userPrefService.getUserOnboarded().collect { isDone ->
                if (isDone) {
                    navigationService.setNavigationCommand(
                        NavigationCommands.TrackerDashboardAsInitial
                    )
                } else {
                    navigationService.setNavigationCommand(
                        NavigationCommands.OnboardingWelcomeAsInitial
                    )
                }
            }
        }
    }
}