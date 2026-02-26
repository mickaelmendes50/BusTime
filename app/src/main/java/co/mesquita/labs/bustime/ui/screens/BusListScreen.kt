package co.mesquita.labs.bustime.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import co.mesquita.labs.bustime.R
import co.mesquita.labs.bustime.model.BusViewModel
import co.mesquita.labs.bustime.ui.components.BusChip
import co.mesquita.labs.bustime.ui.components.EmptyFragment
import co.mesquita.labs.bustime.ui.components.ShimmerChip
import kotlinx.coroutines.launch

@Composable
fun BusListScreen(
    stopId: Int,
    navController: NavController,
    viewModel: BusViewModel = viewModel()
) {
    val columnState = rememberScalingLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val busList by viewModel.busList.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)

    LifecycleResumeEffect(stopId) {
        viewModel.updateBusTable(stopId)
        onPauseOrDispose { }
    }

    LaunchedEffect(Unit) {
        columnState.animateScrollToItem(0)
    }

    LaunchedEffect(stopId) {
        viewModel.updateBusTable(stopId)
    }

    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = columnState
    ) {
        item(key = "title") {
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

        // if loading show shimmer
        if (busList.isEmpty() && isLoading) items(3, key = { "shimmer-$it" }) {
            ShimmerChip()
        }

        // if loading done but empty show empty fragment
        else if (busList.isEmpty()) item {
            EmptyFragment(stringResource(R.string.empty_bus_list))
        }

        // show the BusChip for each bus info
        else items(busList.size) { i ->
            BusChip(
                destination = busList[i].destination,
                number = busList[i].number,
                next = busList[i].next,
                following = busList[i].following,
                onClick = { /* navController.navigate("bus-tracking") */ }
            )
        }

        item(key = "refresh") {
            Chip(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 16.dp),
                onClick = {
                    viewModel.resetBusList()
                    viewModel.updateBusTable(stopId)
                    coroutineScope.launch {
                        columnState.animateScrollToItem(0)
                    }
                },
                label = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Refresh,
                            contentDescription = "Update",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = stringResource(R.string.refresh),
                            fontSize = 12.sp
                        )
                    }
                },
                enabled = !isLoading,
                colors = ChipDefaults.chipColors(
                    backgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.1f)
                )
            )
        }
    }
}
