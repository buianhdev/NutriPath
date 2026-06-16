package vn.anhbt.nutripath.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val route: Routes,
    val title: String,
    val icon: ImageVector
)

val listBottomItems = listOf(
    BottomNavItem(Routes.HomeRoute, "Home", Icons.Outlined.Home),
    BottomNavItem(Routes.DailyLogRoute, "Daily", Icons.Outlined.DateRange),
    BottomNavItem(Routes.ProfileRoute, "Profile", Icons.Outlined.Person),
)