/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package co.mesquita.labs.bustime.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import co.mesquita.labs.bustime.R
import co.mesquita.labs.bustime.model.BusViewModel
import co.mesquita.labs.bustime.presentation.theme.BusTimeGoianiaTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val busStop = mutableStateOf("")
    val viewModel: BusViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        //this.viewModel = ViewModelProvider(this)[co.mesquita.labs.bustime.model.BusViewModel::class.java]

        setContent {
            WearApp()
        }
    }

    @Composable
    fun WearApp() {
        BusTimeGoianiaTheme {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background),
                contentAlignment = Alignment.Center
            ) {
                TimeText()
                Column {
                    Greeting()
                    StopBusTextField()
                    Button()
                }

            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun StopBusTextField() {
        var text by remember { mutableStateOf("") }
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
                busStop.value = it
            },
            label = {
                Text(
                    text = stringResource(
                        id = R.string.text_field_label
                    ),
                    style = TextStyle(
                        color = Color.Gray,
                    )
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary,
            ),
            textStyle = TextStyle(color = Color.White),
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .padding(bottom = 15.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = CircleShape,
        )
    }

    @Composable
    fun Greeting() {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .padding(top = 15.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = MaterialTheme.colors.primary,
            text = stringResource(R.string.title)
        )
    }

    @Composable
    fun Button(
//        viewModel: BusViewModel = viewModel()
    ) {
        //val viewModel: BusViewModel = viewModel()
        //this.viewModel.getBussTime("1234")

        Button(
            onClick = {
                Log.d("test", busStop.value)
            },
            modifier = Modifier
                .fillMaxWidth()
                .size(35.dp)
                .padding(horizontal = 70.dp),
        ) {
            Text(stringResource(id = R.string.search_button))
        }
    }

    @Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
    @Composable
    fun DefaultPreview() {
        WearApp()
    }
}
