package com.luisma.router

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.luisma.core_ui.services.NavigationAction
import com.luisma.core_ui.services.NavigationCommands
import com.luisma.core_ui.services.NavigationService
import com.luisma.onboarding_ui.pages.onbaording_age.OnboardingAge
import com.luisma.onboarding_ui.pages.onbaording_age.OnboardingAgeVM
import com.luisma.onboarding_ui.pages.onboarding_activity.OnboardingActivity
import com.luisma.onboarding_ui.pages.onboarding_activity.OnboardingActivityVM
import com.luisma.onboarding_ui.pages.onboarding_gender.OnboardingGender
import com.luisma.onboarding_ui.pages.onboarding_gender.OnboardingGenderVM
import com.luisma.onboarding_ui.pages.onboarding_goal.OnboardingGoal
import com.luisma.onboarding_ui.pages.onboarding_goal.OnboardingGoalVM
import com.luisma.onboarding_ui.pages.onboarding_height.OnboardingHeight
import com.luisma.onboarding_ui.pages.onboarding_height.OnboardingHeightVM
import com.luisma.onboarding_ui.pages.onboarding_nutrients.OnboardingNutrients
import com.luisma.onboarding_ui.pages.onboarding_nutrients.OnboardingNutrientsVM
import com.luisma.onboarding_ui.pages.onboarding_weight.OnboardingWeight
import com.luisma.onboarding_ui.pages.onboarding_weight.OnboardingWeightVM
import com.luisma.onboarding_ui.pages.onboarding_welcome.OnboardingWelcome
import com.luisma.onboarding_ui.pages.onboarding_welcome.OnboardingWelcomeVM
import com.luisma.startup_ui.Startup
import com.luisma.startup_ui.StartupVM
import com.luisma.tracker_ui.pages.tracker_search_food.TrackerSearchFood
import com.luisma.tracker_ui.pages.tracker_search_food.TrackerSearchFoodVM
import com.luisma.tracker_ui.pages.tracker_dashboard.TrackerDashboard
import com.luisma.tracker_ui.pages.tracker_dashboard.TrackerDashboardVM
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun NavHostController.putAsStartRoute(route: String) {
    popBackStack(graph.startDestinationId, true)
    graph.setStartDestination(route)
    navigate(route)
}

@Composable
fun Router(
    navigationService: NavigationService,
) {

    val navController = rememberNavController()
    val navigationCoroutine = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = NavigationCommands.Startup.route
    ) {

        navigationCoroutine.launch {
            navigationService.navigationCommands.collectLatest { navigationCommand ->
                when (navigationCommand.navigationAction) {
                    NavigationAction.Forward -> navController.navigate(navigationCommand.route)
                    NavigationAction.PutAsStartRoute -> navController.putAsStartRoute(
                        navigationCommand.route
                    )
                    NavigationAction.GoBack -> navController.popBackStack()
                }
            }
        }

        composable(
            route = NavigationCommands.Startup.route
        ) {
            hiltViewModel<StartupVM>()
            Startup()
        }

        composable(
            route = NavigationCommands.OnboardingWelcomeAsInitial.route
        ) {
            val vm = hiltViewModel<OnboardingWelcomeVM>()
            OnboardingWelcome(
                eventDispatcher = vm::eventDispatcher
            )
        }

        composable(
            route = NavigationCommands.OnbaordingGender.route
        ) {
            val vm = hiltViewModel<OnboardingGenderVM>()
            OnboardingGender(
                state = vm.state,
                eventDispatcher = vm::eventDispatcher
            )
        }

        composable(
            route = NavigationCommands.OnboardingAge.route
        ) {
            val vm = hiltViewModel<OnboardingAgeVM>()
            OnboardingAge(
                state = vm.state,
                eventDispatcher = vm::eventDispatcher
            )
        }

        composable(
            route = NavigationCommands.OnbaordingHeight.route
        ) {
            val vm = hiltViewModel<OnboardingHeightVM>()
            OnboardingHeight(
                state = vm.state,
                eventDispatcher = vm::eventDispatcher
            )
        }

        composable(
            route = NavigationCommands.OnboardingWeight.route
        ) {
            val vm = hiltViewModel<OnboardingWeightVM>()
            OnboardingWeight(
                state = vm.state,
                eventDispatcher = vm::eventDispatcher
            )
        }

        composable(
            route = NavigationCommands.OnboardingActivityLevel.route
        ) {
            val vm = hiltViewModel<OnboardingActivityVM>()
            OnboardingActivity(
                eventDispatcher = vm::eventDispatcher,
                state = vm.state
            )
        }

        composable(
            route = NavigationCommands.OnboardingGoal.route
        ) {
            val vm = hiltViewModel<OnboardingGoalVM>()
            OnboardingGoal(
                state = vm.state,
                eventDispatcher = vm::eventDispatcher
            )
        }

        composable(
            route = NavigationCommands.OnboardingNutrientsGoal.route
        ) {
            val vm = hiltViewModel<OnboardingNutrientsVM>()
            OnboardingNutrients(
                state = vm.state,
                eventDispatcher = vm::eventDispatcher
            )
        }

        composable(
            route = NavigationCommands.TrackerDashboardAsInitial.route
        ) {
            val vm = hiltViewModel<TrackerDashboardVM>()
            TrackerDashboard(
                eventDispatcher = vm::eventDispatcher,
                state = vm.state
            )
        }

        composable(
            route = NavigationCommands.TrackerSearchFood.fullRoute,
            arguments = NavigationCommands.TrackerSearchFood.namedNavArguments
        ) {
            val vm = hiltViewModel<TrackerSearchFoodVM>()
            TrackerSearchFood(
                state = vm.state,
                eventDispatcher = vm::eventDispatcher
            )
        }

    }
}