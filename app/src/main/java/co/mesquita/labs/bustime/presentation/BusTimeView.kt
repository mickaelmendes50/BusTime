package co.mesquita.labs.bustime.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.ExperimentalWearFoundationApi
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import co.mesquita.labs.bustime.Constants
import co.mesquita.labs.bustime.R
import co.mesquita.labs.bustime.presentation.theme.BusTimeGoianiaTheme
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.rotaryinput.rotaryWithScroll
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class BusTimeTable : ComponentActivity() {
    private val busStop = mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val htmlContent = intent.getStringExtra(Constants.EXTRA_RESULT).toString()
        val document: Document = Jsoup.parse(htmlContent)
        busStop.value = getBusStop(document)

        if (busStop.value.isEmpty()) {
            setContent {
                EmptyTable()
            }
        } else {
            setContent {
                ShowTable(document, busStop.value)
            }
        }
    }

    private fun getBusStop(document: Document): String {
        val title = document.select("title")
        return title.text().substringAfterLast("Ponto ID:")
    }
}

@OptIn(ExperimentalWearFoundationApi::class, ExperimentalHorologistApi::class)
@Composable
fun ShowTable(document: Document, busStop: String) {
    BusTimeGoianiaTheme {
        val listState = rememberScalingLazyListState()

        Scaffold(
            timeText = {
                TimeText()
            },
            vignette = {
                Vignette(vignettePosition = VignettePosition.TopAndBottom)
            },
            positionIndicator = {
                PositionIndicator(
                    scalingLazyListState = listState
                )
            }
        ) {
            ScalingLazyColumn(
                modifier = Modifier
                    .rotaryWithScroll(listState)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                state = listState
            ) {
                item {
                    Greeting(busStop)
                }

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
                        Chip(
                            modifier = Modifier.fillMaxWidth(),
                            label = {
                                Column {
                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 8.dp),
                                        textAlign = TextAlign.Center,
                                        fontSize = 8.sp,
                                        color = MaterialTheme.colors.onSurfaceVariant,
                                        text = destiny,
                                    )
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 8.dp)
                                            .padding(bottom = 8.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 17.sp,
                                                color = MaterialTheme.colors.primary,
                                                text = busNumber,
                                            )
                                            Text(
                                                fontSize = 8.sp,
                                                color = MaterialTheme.colors.onSurfaceVariant,
                                                text = stringResource(R.string.table_line),
                                            )
                                        }
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                fontSize = 17.sp,
                                                color = MaterialTheme.colors.onSurface,
                                                text = nextTime,
                                            )
                                            Text(
                                                fontSize = 8.sp,
                                                color = MaterialTheme.colors.onSurfaceVariant,
                                                text = stringResource(R.string.table_next),
                                            )
                                        }
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                fontSize = 17.sp,
                                                color = MaterialTheme.colors.secondary,
                                                text = anotherNext,
                                            )
                                            Text(
                                                fontSize = 8.sp,
                                                color = MaterialTheme.colors.onSurfaceVariant,
                                                text = stringResource(R.string.table_another_next),
                                            )
                                        }
                                    }
                                }
                            },
                            colors = ChipDefaults.primaryChipColors(backgroundColor = MaterialTheme.colors.surface),
                            enabled = false,
                            onClick = { /*TODO*/ }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyTable() {
    BusTimeGoianiaTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            TimeText()
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
}

@Composable
fun Greeting(busStop: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = MaterialTheme.colors.primary,
        text = stringResource(R.string.title_table, busStop)
    )
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun GreetingPreview() {
    BusTimeGoianiaTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            TimeText()
        }
    }
}
