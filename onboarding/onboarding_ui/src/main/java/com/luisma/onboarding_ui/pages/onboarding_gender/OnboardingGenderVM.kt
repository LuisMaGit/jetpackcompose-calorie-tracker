package com.luisma.onboarding_ui.pages.onboarding_gender

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisma.core.models.GenderUserProfile
import com.luisma.core.services.preferences_service.IUserPreferencesService
import com.luisma.core_ui.services.NavigationCommands
import com.luisma.core_ui.services.NavigationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingGenderVM @Inject constructor(
    private val navigationService: NavigationService,
    private val userProfileStoreService: IUserPreferencesService
) : ViewModel() {

    var state by mutableStateOf(OnboardingGenderModel())
        private set

    private fun setGender(gender: GenderUserProfile) {
        if (state.genderSelected == gender) return
        state = state.copy(genderSelected = gender)
    }

    private suspend fun goToAge() {
        userProfileStoreService.getUserProfile().collect { pref ->
            userProfileStoreService.setUserProfile(pref.copy(gender = state.genderSelected))
            navigationService.setNavigationCommand(NavigationCommands.OnboardingAge)
        }
    }

    fun eventDispatcher(event: OnboardingGenderEvents) {
        when (event) {
            is OnboardingGenderEvents.TapGender -> {
                setGender(event.gender)
            }
            is OnboardingGenderEvents.TapNext -> viewModelScope.launch {
                goToAge()
            }
        }
    }
}