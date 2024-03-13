package co.mesquita.labs.bustime.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
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
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.TimeText
import co.mesquita.labs.bustime.Constants
import co.mesquita.labs.bustime.R
import co.mesquita.labs.bustime.presentation.theme.BusTimeGoianiaTheme
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class BusTimeTable : ComponentActivity() {
    private val busStop = mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent

        if (intent.hasExtra(Constants.EXTRA_RESULT)) {
            val htmlContent = intent.getStringExtra(Constants.EXTRA_RESULT).toString()
            val document: Document = Jsoup.parse(htmlContent)
            busStop.value = getBusStop(document)
            handleHtmlContent(htmlContent)
        }

        if (busStop.value.isEmpty()) {
            setContent {
                EmptyTable()
            }
        } else {
            setContent {
                ShowTable()
            }
        }
    }

    private fun getBusStop(document: Document): String {
        val title = document.select("title")

        return title.toString()
            .substringAfterLast("Ponto ID:")
            .substringBefore("</title>")
            .trim()
    }

    private fun handleHtmlContent(htmlContent: String) {
        val document: Document = Jsoup.parse(htmlContent)
        val title = document.select("title")

        this.busStop.value = title.toString()
            .substringAfterLast("Ponto ID:")
            .substringBefore("</title>")
            .trim()
        val timeTable = document.select("table.horariosRmtc")
        Log.d("test", busStop.value.isEmpty().toString())
    }
}

@Composable
fun ShowTable() {
    BusTimeGoianiaTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
        ) {
            TimeText()
            Greeting()
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
fun Greeting() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .padding(top = 25.dp),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = MaterialTheme.colors.primary,
        text = stringResource(R.string.title_table)
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
            Greeting()
        }
    }
}
