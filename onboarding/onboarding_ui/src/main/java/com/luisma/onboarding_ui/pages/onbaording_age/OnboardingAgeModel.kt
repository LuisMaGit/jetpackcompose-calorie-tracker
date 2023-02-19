package com.luisma.onboarding_ui.pages.onbaording_age

data class OnboardingAgeModel(
    val age: Int = 27
){
    fun invalidAge() : Boolean = age == 0
}