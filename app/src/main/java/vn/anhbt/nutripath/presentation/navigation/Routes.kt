package vn.anhbt.nutripath.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Routes {

    // Onboarding graph
    @Serializable
    data object FirstOpen : Routes()

    @Serializable
    data object PlanningScreen : Routes()

    @Serializable
    data object PlanningResultScreen : Routes()

    // Main graph
    @Serializable
    data object HomeScreen : Routes()

    @Serializable
    data object DailyLogScreen : Routes()

    @Serializable
    data object ProfileScreen : Routes()

}
sealed class NestedGraph {
    @Serializable
    data object OnboardingGraph : NestedGraph()

    @Serializable
    data object MainGraph : NestedGraph()
}