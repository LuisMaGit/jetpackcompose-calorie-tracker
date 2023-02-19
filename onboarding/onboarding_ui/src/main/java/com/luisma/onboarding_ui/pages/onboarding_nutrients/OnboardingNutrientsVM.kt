package com.luisma.onboarding_ui.pages.onboarding_nutrients

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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingNutrientsVM @Inject constructor(
    private val navigationService: NavigationService,
    private val userProfileStoreService: IUserPreferencesService,
    private val stringHelperService: StringHelperService
) : ViewModel() {

    var state by mutableStateOf(OnboardingNutrientsModel())
        private set


    private fun setFormValue(
        type: NutrientsFormType,
        valueStr: String
    ) {

        val value = stringHelperService.toInt(valueStr)
        val currentValue = state.formsMap.getValue(type)
        if (value == currentValue) {
            return
        }
        val mutableFormMap = state.formsMap.toMutableMap()
        mutableFormMap[type] = value
        state = state.copy(formsMap = mutableFormMap)
    }

    private suspend fun goToTracker() {
        userProfileStoreService.getUserProfile().collect { pref ->
            userProfileStoreService.setUserProfile(
                pref.copy(
                    carbRatio = state.formsMap.getValue(NutrientsFormType.Carb),
                    proteinRatio = state.formsMap.getValue(NutrientsFormType.Protein),
                    fatRatio = state.formsMap.getValue(NutrientsFormType.Fat),
                )
            )
            userProfileStoreService.setUserOnboarded()
            navigationService.setNavigationCommand(
                NavigationCommands.TrackerDashboardAsInitial
            )
        }
    }

    fun eventDispatcher(event: OnboardingNutrientsEvents) {
        when (event) {
            is OnboardingNutrientsEvents.TapNext -> viewModelScope.launch {
                goToTracker()
            }
            is OnboardingNutrientsEvents.SetNutrients -> setFormValue(
                type = event.typeNutrientForm,
                valueStr = event.valueNutrient,
            )
        }
    }
}