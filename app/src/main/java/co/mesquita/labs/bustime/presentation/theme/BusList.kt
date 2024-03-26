package co.mesquita.labs.bustime.presentation.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import co.mesquita.labs.bustime.R
import co.mesquita.labs.bustime.components.BusChip
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.layout.ScalingLazyColumn
import com.google.android.horologist.compose.layout.ScreenScaffold
import com.google.android.horologist.compose.layout.rememberResponsiveColumnState
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

@OptIn(ExperimentalHorologistApi::class)
@Composable
fun BusListScreen(
    stopNumber: String,
    htmlContent: String
) {
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
                    text = stringResource(R.string.table_title, stopNumber)
                )
            }
            val document: Document = Jsoup.parse(htmlContent)
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
