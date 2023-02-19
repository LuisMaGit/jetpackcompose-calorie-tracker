package com.luisma.onboarding_ui.pages.onboarding_nutrients

data class OnboardingNutrientsModel(
    val formsMap: Map<NutrientsFormType, Int> = mapOf(
        NutrientsFormType.Carb to 40,
        NutrientsFormType.Protein to 30,
        NutrientsFormType.Fat to 30,
    )
) {

    fun areAllFormsValid(): Boolean {
        var sum = 0
        for (f in NutrientsFormType.values()){
            sum += formsMap[f]!!
        }
        return sum == 100
    }
}

enum class NutrientsFormType {
    Carb,
    Protein,
    Fat
}