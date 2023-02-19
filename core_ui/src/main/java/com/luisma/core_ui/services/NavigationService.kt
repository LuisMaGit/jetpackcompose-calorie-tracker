package com.luisma.core_ui.services

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.luisma.core.models.food.MealType
import com.luisma.core.models.food.ProductsFood
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

enum class NavigationAction {
    Forward, PutAsStartRoute, GoBack
}

interface INavigationCommand {
    val route: String
    val navigationAction: NavigationAction
        get() = NavigationAction.Forward
    val arguments: List<NamedNavArgument>
        get() = listOf()
}

object NavigationCommands {

    val GoBack = object : INavigationCommand {
        override val route: String
            get() = "GoBack"
        override val navigationAction = NavigationAction.GoBack
    }

    val Startup = object : INavigationCommand {
        override val route: String
            get() = "Startup"
    }

    val OnboardingWelcomeAsInitial = object : INavigationCommand {
        override val route: String
            get() = "OnboardingWelcomeAsInitial"
        override val navigationAction = NavigationAction.PutAsStartRoute
    }

    val OnbaordingGender = object : INavigationCommand {
        override val route: String
            get() = "OnbaordingGender"
    }

    val OnboardingAge = object : INavigationCommand {
        override val route: String
            get() = "OnboardingAge"
    }

    val OnbaordingHeight = object : INavigationCommand {
        override val route: String
            get() = "OnbaordingHeight"
    }

    val OnboardingWeight = object : INavigationCommand {
        override val route: String
            get() = "OnboardingWeight"
    }

    val OnboardingActivityLevel = object : INavigationCommand {
        override val route: String
            get() = "OnboardingActivityLevel"
    }

    val OnboardingGoal = object : INavigationCommand {
        override val route: String
            get() = "OnboardingGoal"
    }

    val OnboardingNutrientsGoal = object : INavigationCommand {
        override val route: String
            get() = "OnboardingNutrientsGoal"
    }

    val TrackerDashboardAsInitial = object : INavigationCommand {
        override val route: String
            get() = "TrackerDashboardAsInitial"
        override val navigationAction = NavigationAction.PutAsStartRoute
    }


    object TrackerSearchFood {
        const val baseRoute = "TrackerSearchFood"
        const val key = "type_food"
        const val fullRoute = "$baseRoute/{$key}"
        val namedNavArguments: List<NamedNavArgument> = listOf(navArgument(name = key) {
            type = NavType.StringType
        })
    }

    data class TrackerSearchFoodFromTrackerDashboard(val foodType: String?) : INavigationCommand {
        override val route: String
            get() = "${TrackerSearchFood.baseRoute}/$foodType"
        override val arguments: List<NamedNavArgument>
            get() = TrackerSearchFood.namedNavArguments
    }

}


sealed class GoBackNavigationCommand {
    data class TrackerFromSearchToDashboard(
        val mealType: MealType,
        val food: ProductsFood,
    ) : GoBackNavigationCommand()
}

class NavigationService(
    private val navigationFlow: MutableSharedFlow<INavigationCommand>,
    private val returnNavigationFlow: MutableSharedFlow<GoBackNavigationCommand>,
) {

    val navigationCommands = navigationFlow.asSharedFlow()

    suspend fun setNavigationCommand(
        command: INavigationCommand,
    ) {
        navigationFlow.emit(command)
    }


    val returnNavigationCommands = returnNavigationFlow.asSharedFlow()

    suspend fun setGoBackNavigationCommand(
        command: GoBackNavigationCommand,
    ) {
        returnNavigationFlow.emit(command)
    }


}