package co.mesquita.labs.bustime.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import co.mesquita.labs.bustime.ui.screens.BusListScreen
import co.mesquita.labs.bustime.ui.screens.HomeScreen
import co.mesquita.labs.bustime.ui.screens.NotFoundScreen
import co.mesquita.labs.bustime.ui.screens.SearchScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    isAmbient: Boolean
) {
    SwipeDismissableNavHost(
        navController = navController,
        startDestination = NavigationRoutes.HOME_SCREEN.route,
        userSwipeEnabled = true
    ) {
        composable(NavigationRoutes.HOME_SCREEN.route) {
            HomeScreen(navController)
        }
        composable(NavigationRoutes.SEARCH_SCREEN.route) {
            SearchScreen(navController)
        }
        composable(NavigationRoutes.NOT_FOUND_SCREEN.route) {
            NotFoundScreen()
        }
        composable(
            route = "${NavigationRoutes.BUS_LIST_SCREEN.route}?stopId={stopId}",
            arguments = listOf(
                navArgument("stopId") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) { backStackEntry ->
            val stopId = backStackEntry.arguments?.getInt("stopId") ?: 0
            BusListScreen(
                stopId = stopId,
                isAmbient = isAmbient,
                navController = navController
            )
        }
        composable(NavigationRoutes.BUS_TRACKING_SCREEN.route) {
            //BusTrackingScreen()
        }
    }
}
