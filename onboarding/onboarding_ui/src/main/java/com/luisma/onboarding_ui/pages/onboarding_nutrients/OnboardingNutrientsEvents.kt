package com.luisma.onboarding_ui.pages.onboarding_nutrients

sealed class OnboardingNutrientsEvents {
    object TapNext : OnboardingNutrientsEvents()
    class SetNutrients(
        val typeNutrientForm: NutrientsFormType,
        val valueNutrient: String
    ) : OnboardingNutrientsEvents()
}