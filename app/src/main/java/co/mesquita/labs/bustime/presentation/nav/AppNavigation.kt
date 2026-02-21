package co.mesquita.labs.bustime.presentation.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import co.mesquita.labs.bustime.presentation.screens.BusListScreen
import co.mesquita.labs.bustime.presentation.screens.HomeScreen
import co.mesquita.labs.bustime.presentation.screens.SearchScreen
import co.mesquita.labs.bustime.presentation.screens.NotFoundScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    SwipeDismissableNavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(navController)
        }
        composable("search") {
            SearchScreen(navController)
        }
        composable("not-found") {
            NotFoundScreen()
        }
        composable(
            route = "bus-list?stopId={stopId}",
            arguments = listOf(
                navArgument("stopId") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) { backStackEntry ->
            val stopId = backStackEntry.arguments?.getInt("stopId") ?: 0
            BusListScreen(navController = navController, stopId = stopId)
        }
        composable("bus-tracking") {
            //BusTrackingScreen()
        }
    }
}
