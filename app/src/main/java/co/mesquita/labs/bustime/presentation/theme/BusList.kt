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
    stopId: String,
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
                    text = stringResource(R.string.table_title, stopId)
                )
            }
            val document: Document = Jsoup.parse(htmlContent)
            val timeTable = document.select("table.table-sm.table-striped.subtab-previsoes")
            for (line in timeTable.select("tr").drop(1)) {
                val columns = line.select("td")
                val busNumber = columns[0].text()
                val destiny = columns[1].text()
                val nextTimeElement = columns[2]
                val anotherNextElement = columns[3]

                val nextTime = if (nextTimeElement.text() != "--") {
                    nextTimeElement.ownText().replace(Regex("[^\\d]+"), "").replace("(Aprox.)", "?")
                } else {
                    "--"
                }

                val anotherNext = if (anotherNextElement.text() != "--") {
                    anotherNextElement.ownText().replace(Regex("[^\\d]+"), "").replace("(Aprox.)", "?")
                } else {
                    "--"
                }

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
