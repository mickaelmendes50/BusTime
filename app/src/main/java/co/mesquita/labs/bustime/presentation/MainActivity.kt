package co.mesquita.labs.bustime.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import co.mesquita.labs.bustime.model.BusViewModel
import co.mesquita.labs.bustime.presentation.theme.BusListScreen
import co.mesquita.labs.bustime.presentation.theme.BusTimeGoianiaTheme
import co.mesquita.labs.bustime.presentation.theme.HomeScreen
import co.mesquita.labs.bustime.presentation.theme.NotFoundScreen
import co.mesquita.labs.bustime.presentation.theme.SearchScreen
import com.google.android.horologist.compose.layout.AppScaffold

class MainActivity : ComponentActivity() {

    private val viewModel: BusViewModel by viewModels()
    private val busStop = mutableStateOf("")
    private val htmlContent = mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
            WearApp()
        }
    }

    private fun onSearchButtonClick(navController: NavController, stopId: String) {
        this.viewModel.isValidStopId(stopId).observe(this) { isValid ->
            if (!isValid) {
                navController.navigate("notFound")
            } else {
                this.viewModel.getBussTime(stopId).observe(this) {
                    htmlContent.value = it
                    busStop.value = stopId
                    navController.navigate("busList")
                }
            }
        }
    }

    @Composable
    fun WearApp() {
        val navController = rememberSwipeDismissableNavController()

        BusTimeGoianiaTheme {
            AppScaffold {
                SwipeDismissableNavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    composable("home") {
                        HomeScreen(navController)
                    }
                    composable("search") {
                        val isLoading by viewModel.isLoading.observeAsState()
                        SearchScreen(isLoading, navController) { navController, stopNumber ->
                            onSearchButtonClick(navController, stopNumber)
                        }
                    }
                    composable("notFound") {
                        NotFoundScreen()
                    }
                    composable("busList") {
                        BusListScreen(
                            stopId = busStop.value,
                            htmlContent = htmlContent.value
                        )
                    }
                }
            }
        }
    }
}
