package co.mesquita.labs.bustime.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import co.mesquita.labs.bustime.R
import co.mesquita.labs.bustime.ui.components.RoundTextField
import co.mesquita.labs.bustime.model.BusViewModel
import com.google.android.horologist.compose.layout.fillMaxRectangle

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: BusViewModel = viewModel()
) {
    val isLoading by viewModel.isLoading.observeAsState(false)
    val isValidStopId by viewModel.isValidStopId.observeAsState()

    var stopId by remember { mutableStateOf("") }

    LaunchedEffect(isValidStopId) {
        when (isValidStopId) {
            true  -> {
                navController.navigate("bus-list?stopId=$stopId")
                viewModel.resetValidation()
            }
            false -> {
                navController.navigate("not-found")
                viewModel.resetValidation()
            }
            else  -> Unit
        }
    }

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
            RoundTextField(
                input = stopId,
                keyboardType = KeyboardType.Number,
                onValueChange = { newValue ->
                    stopId = newValue
                },
                onDone = { text ->
                    viewModel.validateStopId(text.toInt())
                }
            )
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
