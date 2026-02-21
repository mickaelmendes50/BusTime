package co.mesquita.labs.bustime.ui.screens

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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import co.mesquita.labs.bustime.R
import co.mesquita.labs.bustime.ui.components.BusChip
import co.mesquita.labs.bustime.model.BusViewModel
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.layout.ScalingLazyColumn
import com.google.android.horologist.compose.layout.rememberResponsiveColumnState
import com.valentinilk.shimmer.shimmer

@OptIn(ExperimentalHorologistApi::class)
@Composable
fun BusListScreen(
    stopId: Int,
    navController: NavController,
    viewModel: BusViewModel = viewModel()
) {
    val columnState = rememberResponsiveColumnState()
    val busList by viewModel.busList.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(true)

    LaunchedEffect(stopId) {
        viewModel.updateBusTable(stopId)
    }

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

        if (isLoading)
            items(3) {
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
                    onClick = { /* do nothing */ }
                )
            }

        // Create a BusChip for each bus info
        items(busList.size) { i ->
            BusChip(
                destination = busList[i].destination,
                number = busList[i].number,
                next = busList[i].next,
                following = busList[i].following,
                onClick = { /* navController.navigate("bus-tracking") */ }
            )
        }
    }
}
