package vn.anhbt.nutripath.presentation.onboarding.planning.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import vn.anhbt.nutripath.presentation.navigation.Routes
import vn.anhbt.nutripath.presentation.onboarding.planning.PlanningScreen

fun NavController.navigateToPlanning(
    navOptions: NavOptions? = null
) {
    navigate(Routes.PlanningRoute, navOptions)
}

fun NavGraphBuilder.planningScreen(
    onContinue: () -> Unit = {}
) {
    composable<Routes.PlanningRoute> {
        PlanningScreen(onContinue = onContinue)
    }
}
