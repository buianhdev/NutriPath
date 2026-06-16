package vn.anhbt.nutripath.presentation.main.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import vn.anhbt.nutripath.presentation.main.profile.ProfileScreen
import vn.anhbt.nutripath.presentation.navigation.Routes

fun NavController.navigateToProfile(
    navOptions: NavOptions? = null
) {
    navigate(Routes.ProfileRoute, navOptions)
}

fun NavGraphBuilder.profileScreen() {
    composable<Routes.ProfileRoute> {
        ProfileScreen()
    }
}
