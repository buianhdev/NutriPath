package vn.anhbt.nutripath

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import vn.anhbt.nutripath.presentation.main.mainGraph
import vn.anhbt.nutripath.presentation.navigation.NestedGraph
import vn.anhbt.nutripath.presentation.onboarding.onboardingGraph

@Composable
fun RootGraph(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NestedGraph.OnboardingGraph
    ) {
        onboardingGraph(navController)

        mainGraph()
    }
}