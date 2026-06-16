package vn.anhbt.nutripath.presentation.main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import vn.anhbt.nutripath.presentation.main.dailylog.navigation.dailyLogScreen
import vn.anhbt.nutripath.presentation.main.dailylog.navigation.navigateToDailyLog
import vn.anhbt.nutripath.presentation.main.home.navigation.homeScreen
import vn.anhbt.nutripath.presentation.main.profile.navigation.navigateToProfile
import vn.anhbt.nutripath.presentation.main.profile.navigation.profileScreen
import vn.anhbt.nutripath.presentation.navigation.NestedGraph
import vn.anhbt.nutripath.presentation.navigation.Routes

fun NavGraphBuilder.mainGraph(
    navController: NavController
) {
    navigation<NestedGraph.MainGraph>(
        startDestination = Routes.HomeScreen
    ) {
        homeScreen(
            onOpenDailyLog = { navController.navigateToDailyLog() },
            onOpenProfile = { navController.navigateToProfile() }
        )
        dailyLogScreen()
        profileScreen()
    }
}
