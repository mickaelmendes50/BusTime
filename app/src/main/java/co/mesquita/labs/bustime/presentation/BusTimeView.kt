package co.mesquita.labs.bustime.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.lazy.AutoCenteringParams
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import androidx.wear.protolayout.material.Chip
import co.mesquita.labs.bustime.Constants
import co.mesquita.labs.bustime.R
import co.mesquita.labs.bustime.presentation.theme.BusTimeGoianiaTheme
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

@Composable
fun TableHeader(
    text: String,
    textColor: Color = Color.Black
) {
    Text(
        text = text,
        modifier = Modifier
            .padding(8.dp),
        color = textColor,
        fontSize = 12.sp
    )
}

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
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                state = listState
            ) {
                item {
                    Greeting(busStop)
                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Gray),
                    ) {
                        TableHeader(text = "Linha", textColor = Color.White)
                        TableHeader(text = "Próximo", textColor = Color.White)
                        TableHeader(text = "Seguinte", textColor = Color.White)
                    }
                }

                val timeTable = document.select("table.horariosRmtc")
                for (line in timeTable.select("tr.linha").drop(1)) {
                    val columns = line.select("td.coluna")
                    val busNumber = columns[0].text()
                    Log.d("test", busNumber.toString())
                    val nextTime = columns[2].text()
                    val anotherNext = columns[3].text()

                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            TableHeader(text = busNumber, textColor = Color.White)
                            TableHeader(text = nextTime, textColor = Color.White)
                            TableHeader(text = anotherNext, textColor = Color.White)
                        }
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
        modifier = Modifier.fillMaxWidth(),
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
