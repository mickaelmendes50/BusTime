package co.mesquita.labs.bustime.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import co.mesquita.labs.bustime.R
import co.mesquita.labs.bustime.components.BusChip
import co.mesquita.labs.bustime.model.BusViewModel
import co.mesquita.labs.bustime.presentation.theme.BusTimeGoianiaTheme
import co.mesquita.labs.bustime.presentation.theme.HomeScreen
import co.mesquita.labs.bustime.presentation.theme.NotFoundScreen
import co.mesquita.labs.bustime.presentation.theme.SearchScreen
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.layout.AppScaffold
import com.google.android.horologist.compose.layout.ScalingLazyColumn
import com.google.android.horologist.compose.layout.ScreenScaffold
import com.google.android.horologist.compose.layout.rememberResponsiveColumnState
import com.valentinilk.shimmer.shimmer

class MainActivity : ComponentActivity() {

    private val viewModel: BusViewModel by viewModels()
    private lateinit var mStopId: String;

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
            WearApp()
        }
    }

    private fun onSearchButtonClick(navController: NavController, stopId: String) {
        mStopId = stopId
        this.viewModel.isValidStopId(stopId).observe(this) { isValid ->
            if (!isValid) {
                navController.navigate("notFound")
            } else {
                navController.navigate("busList")
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
                        SearchScreen(isLoading, navController) { navController, stopId ->
                            onSearchButtonClick(navController, stopId)
                        }
                    }
                    composable("notFound") {
                        NotFoundScreen()
                    }
                    composable("busList") {
                        BusListScreen()
                    }
                    composable("busTracking") {
                        //BusTrackingScreen()
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalHorologistApi::class)
    @Composable
    fun BusListScreen() {
        val columnState = rememberResponsiveColumnState()
        val busList by viewModel.busList.observeAsState(emptyList())
        val isLoading by viewModel.isLoading.observeAsState(true)

        LaunchedEffect(mStopId) {
            viewModel.updateBusTable(mStopId)
        }

        ScreenScaffold(scrollState = columnState) {
            ScalingLazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                columnState = columnState
            ) {
                item {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colors.primary,
                        text = stringResource(R.string.table_title, mStopId)
                    )
                }

                if(isLoading) {
                    items(3) {i ->
                        Chip(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shimmer(),
                            label = {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 8.dp),
                                    textAlign = TextAlign.Center,
                                    fontSize = 8.sp,
                                    color = MaterialTheme.colors.onSurfaceVariant,
                                    text = "",
                                )
                            },
                            onClick = { /*TODO*/ }
                        )
                    }
                }

                // Create a BusChip for each bus info
                items(busList.size) { i ->
                    BusChip(
                        destination = busList[i].destination,
                        number = busList[i].number,
                        next = busList[i].next,
                        following = busList[i].following
                    )
                }
            }
        }
    }
}
