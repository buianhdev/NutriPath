package vn.anhbt.nutripath.presentation.onboarding

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navOptions
import androidx.navigation.navigation
import vn.anhbt.nutripath.presentation.navigation.NestedGraph
import vn.anhbt.nutripath.presentation.navigation.Routes
import vn.anhbt.nutripath.presentation.onboarding.firstopen.navigation.firstOpenScreen
import vn.anhbt.nutripath.presentation.onboarding.planning.navigation.navigateToPlanning
import vn.anhbt.nutripath.presentation.onboarding.planning.navigation.planningScreen
import vn.anhbt.nutripath.presentation.onboarding.planningresult.navigation.navigateToPlanningResult
import vn.anhbt.nutripath.presentation.onboarding.planningresult.navigation.planningResultScreen

fun NavGraphBuilder.onboardingGraph(
    navController: NavController
) {
    navigation<NestedGraph.OnboardingGraph>(
        startDestination = Routes.FirstOpenRoute
    ) {
        firstOpenScreen(
            onContinue = { navController.navigateToPlanning() }
        )
        planningScreen(
            onContinue = { navController.navigateToPlanningResult() }
        )
        planningResultScreen(
            onFinish = {
                navController.navigate(Routes.MainRoute, navOptions {
                    popUpTo<NestedGraph.OnboardingGraph> { inclusive = true }
                    launchSingleTop = true
                })
            }
        )
    }
}
