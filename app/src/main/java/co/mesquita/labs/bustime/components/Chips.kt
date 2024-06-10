package co.mesquita.labs.bustime.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DirectionsBus
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.TimerOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import co.mesquita.labs.bustime.R
import co.mesquita.labs.bustime.data.ArrivalTime

@Composable
fun BusChip(
    destination: String,
    number: String,
    next: ArrivalTime,
    following: ArrivalTime
) {
    Chip(
        modifier = Modifier.fillMaxWidth(),
        label = {
            Column {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp,
                    color = MaterialTheme.colors.onSurfaceVariant,
                    text = destination,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        //.padding(horizontal = 8.dp)
                        .padding(bottom = 10.dp),
                    //horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp,
                                color = MaterialTheme.colors.primary,
                                text = number
                            )
                            Text(
                                fontSize = 8.sp,
                                color = MaterialTheme.colors.onSurfaceVariant,
                                text = stringResource(R.string.table_line),
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row {
                                Text(
                                    fontSize = 17.sp,
                                    color = MaterialTheme.colors.onSurface,
                                    text = next.time,
                                )
                                if (next.isReal != null) {
                                    if (!next.isReal) {
                                        Icon(
                                            imageVector = Icons.Outlined.TimerOff,
                                            contentDescription = null,
                                            modifier = Modifier.size(10.dp),
                                            tint = Color.Red,
                                        )
                                    } else {
                                        Icon(
                                            imageVector = Icons.Outlined.Schedule,
                                            contentDescription = null,
                                            modifier = Modifier.size(10.dp),
                                            tint = MaterialTheme.colors.primary,
                                        )
                                    }
                                }
                            }
                            Text(
                                fontSize = 8.sp,
                                color = MaterialTheme.colors.onSurfaceVariant,
                                text = stringResource(R.string.table_next),
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row {
                                Text(
                                    fontSize = 17.sp,
                                    color = MaterialTheme.colors.secondary,
                                    text = following.time,
                                )
                                if (following.isReal != null) {
                                    if (!following.isReal) {
                                        Icon(
                                            imageVector = Icons.Outlined.TimerOff,
                                            contentDescription = null,
                                            modifier = Modifier.size(10.dp),
                                            tint = Color.Red,
                                        )
                                    } else {
                                        Icon(
                                            imageVector = Icons.Outlined.Schedule,
                                            contentDescription = null,
                                            modifier = Modifier.size(10.dp),
                                            tint = MaterialTheme.colors.primary,
                                        )
                                    }
                                }
                            }
                            Text(
                                fontSize = 8.sp,
                                color = MaterialTheme.colors.onSurfaceVariant,
                                text = stringResource(R.string.table_another_next),
                            )
                        }
                    }

                }
            }
        },
        colors = ChipDefaults.primaryChipColors(backgroundColor = MaterialTheme.colors.surface),
        enabled = false,
        onClick = { /*TODO*/ }
    )
}
