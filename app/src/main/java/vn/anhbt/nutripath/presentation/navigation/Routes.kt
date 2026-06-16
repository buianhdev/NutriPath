package vn.anhbt.nutripath.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Routes {

    // Onboarding graph
    @Serializable
    data object FirstOpenRoute : Routes()

    @Serializable
    data object PlanningRoute : Routes()

    @Serializable
    data object PlanningResultRoute : Routes()

    // Main graph
    @Serializable
    data object HomeRoute : Routes()

    @Serializable
    data object DailyLogRoute : Routes()

    @Serializable
    data object ProfileRoute : Routes()

    @Serializable
    data object MainRoute : Routes()
}
sealed class NestedGraph {
    @Serializable
    data object OnboardingGraph : NestedGraph()

    @Serializable
    data object MainGraph : NestedGraph()
}
