package vn.anhbt.nutripath.presentation.onboarding.planningresult.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import vn.anhbt.nutripath.presentation.navigation.Routes
import vn.anhbt.nutripath.presentation.onboarding.planningresult.PlanningResultScreen

fun NavController.navigateToPlanningResult(
    navOptions: NavOptions? = null
) {
    navigate(Routes.PlanningResultRoute, navOptions)
}

fun NavGraphBuilder.planningResultScreen(
    onFinish: () -> Unit = {}
) {
    composable<Routes.PlanningResultRoute> {
        PlanningResultScreen(onFinish = onFinish)
    }
}
