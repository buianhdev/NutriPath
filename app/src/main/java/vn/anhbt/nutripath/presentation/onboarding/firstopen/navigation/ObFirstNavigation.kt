package vn.anhbt.nutripath.presentation.onboarding.firstopen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import vn.anhbt.nutripath.presentation.navigation.Routes
import vn.anhbt.nutripath.presentation.onboarding.firstopen.FirstOpenScreen

fun NavController.navigateToFirstOpen(
    navOptions: NavOptions? = null
) {
    navigate(Routes.FirstOpenRoute, navOptions)
}

fun NavGraphBuilder.firstOpenScreen(
    onContinue: () -> Unit = {}
) {
    composable<Routes.FirstOpenRoute> {
        FirstOpenScreen(onContinue = onContinue)
    }
}
