package co.mesquita.labs.bustime.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.TimeText
import co.mesquita.labs.bustime.Constants
import co.mesquita.labs.bustime.R
import co.mesquita.labs.bustime.presentation.theme.BusTimeGoianiaTheme
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class BusTimeTable : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent

        if (intent.hasExtra(Constants.EXTRA_RESULT)) {
            val htmlContent = intent.getStringExtra(Constants.EXTRA_RESULT).toString()
            handleHtmlContent(htmlContent)
        }

        setContent {
            BusTimeGoianiaTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(androidx.wear.compose.material.MaterialTheme.colors.background),
                ) {
                    TimeText()
                    Greeting()
                }
            }
        }
    }

    private fun handleHtmlContent(htmlContent: String) {
        val document: Document = Jsoup.parse(htmlContent)
        val timeTable = document.select("table.horariosRmtc")
        Log.d("test", timeTable.toString())
    }
}

@Composable
fun Greeting() {
    androidx.wear.compose.material.Text(
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
                .background(androidx.wear.compose.material.MaterialTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            TimeText()
            Greeting()
        }
    }
}
