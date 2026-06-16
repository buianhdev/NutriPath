package vn.anhbt.nutripath.presentation.main.dailylog.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import vn.anhbt.nutripath.presentation.main.dailylog.DailyLogScreen
import vn.anhbt.nutripath.presentation.navigation.Routes

fun NavController.navigateToDailyLog(
    navOptions: NavOptions? = null
) {
    navigate(Routes.DailyLogRoute, navOptions)
}

fun NavGraphBuilder.dailyLogScreen() {
    composable<Routes.DailyLogRoute> {
        DailyLogScreen()
    }
}
