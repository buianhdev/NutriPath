package vn.anhbt.nutripath.presentation.main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import vn.anhbt.nutripath.presentation.navigation.Routes

fun NavGraphBuilder.mainGraph() {
    composable<Routes.MainRoute> {
        MainScreen()
    }
}
