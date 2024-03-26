package co.mesquita.labs.bustime.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import co.mesquita.labs.bustime.R
import co.mesquita.labs.bustime.components.BusChip
import co.mesquita.labs.bustime.components.SearchButton
import co.mesquita.labs.bustime.components.SearchTextField
import co.mesquita.labs.bustime.model.BusViewModel
import co.mesquita.labs.bustime.presentation.theme.AppLogo
import co.mesquita.labs.bustime.presentation.theme.BusTimeGoianiaTheme
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.layout.AppScaffold
import com.google.android.horologist.compose.layout.ScalingLazyColumn
import com.google.android.horologist.compose.layout.ScreenScaffold
import com.google.android.horologist.compose.layout.fillMaxRectangle
import com.google.android.horologist.compose.layout.rememberResponsiveColumnState
import dagger.hilt.android.AndroidEntryPoint
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

@AndroidEntryPoint
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

    private fun getBusStop(document: Document): String {
        val title = document.select("title")
        return title.text().substringAfterLast("Ponto ID:")
    }

    private fun onSearchButtonClick(navController: NavController, stopNumber: String) {
        this.viewModel.getBussTime(stopNumber).observe(this) {
            htmlContent.value = it
            val document: Document = Jsoup.parse(it)

            if (getBusStop(document).isEmpty()) {
                navController.navigate("notFound")
            } else {
                navController.navigate("busList")
            }
        }
    }

    @OptIn(ExperimentalHorologistApi::class)
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
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colors.background),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                AppLogo()
                                SearchButton(navController)
                            }
                        }
                    }
                    composable("search") {
                        val isLoading by viewModel.isLoading
                        Box(
                            modifier = Modifier
                                .fillMaxRectangle()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = stringResource(R.string.search_title),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                SearchTextField(navController) { navController, stopNumber ->
                                    onSearchButtonClick(navController, stopNumber)
                                }
                                if (isLoading) {
                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .size(35.dp)
                                            .padding(5.dp)
                                            .aspectRatio(1f),
                                        indicatorColor = MaterialTheme.colors.primary,
                                        trackColor = MaterialTheme.colors.background
                                    )
                                }
                            }
                        }
                    }
                    composable("notFound") {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colors.background),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.WarningAmber,
                                    contentDescription = null,
                                    modifier = Modifier.size(45.dp),
                                    tint = MaterialTheme.colors.primary,
                                )
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(5.dp),
                                    textAlign = TextAlign.Center,
                                    fontSize = 15.sp,
                                    color = MaterialTheme.colors.primary,
                                    text = stringResource(R.string.empty_table)
                                )
                            }
                        }
                    }
                    composable("busList") {
                        val columnState = rememberResponsiveColumnState()
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
                                        text = stringResource(R.string.table_title, busStop.value)
                                    )
                                }
                                val document: Document = Jsoup.parse(htmlContent.value)
                                val timeTable = document.select("table.horariosRmtc")
                                for (line in timeTable.select("tr.linha").drop(1)) {
                                    val columns = line.select("td.coluna")
                                    val busNumber = columns[0].text()
                                    val destiny = columns[1].text()
                                    var nextTime = columns[2].text()
                                    nextTime = nextTime
                                        .replace(Regex("(\\d+) Aprox\\."), "$1\\?")
                                    var anotherNext = columns[3].text()
                                    anotherNext = anotherNext
                                        .replace(Regex("(\\d+) Aprox\\."), "$1\\?")

                                    item { 
                                        BusChip(
                                            destiny = destiny,
                                            busNumber = busNumber,
                                            nextTime = nextTime,
                                            anotherNext = anotherNext
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
