package vn.anhbt.nutripath.presentation.main.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import vn.anhbt.nutripath.presentation.main.home.HomeScreen
import vn.anhbt.nutripath.presentation.navigation.Routes

fun NavController.navigateToHome(
    navOptions: NavOptions? = null
) {
    navigate(Routes.HomeRoute, navOptions)
}

fun NavGraphBuilder.homeScreen(
    onOpenDailyLog: () -> Unit = {},
    onOpenProfile: () -> Unit = {}
) {
    composable<Routes.HomeRoute> {
        HomeScreen(
            onOpenDailyLog = onOpenDailyLog,
            onOpenProfile = onOpenProfile
        )
    }
}
