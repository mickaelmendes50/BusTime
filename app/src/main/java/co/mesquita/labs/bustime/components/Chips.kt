package co.mesquita.labs.bustime.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import co.mesquita.labs.bustime.R

@Composable
fun busChip(
    destiny: String,
    busNumber: String,
    nextTime: String,
    anotherNext: String
) {
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
