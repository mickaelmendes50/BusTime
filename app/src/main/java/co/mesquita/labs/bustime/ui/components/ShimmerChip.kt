package co.mesquita.labs.bustime.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.valentinilk.shimmer.shimmer

@Composable
fun ShimmerChip() {
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
