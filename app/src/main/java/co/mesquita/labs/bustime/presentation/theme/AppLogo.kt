package co.mesquita.labs.bustime.presentation.theme

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DirectionsBus
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import co.mesquita.labs.bustime.R

@Composable
fun AppLogo() {
    Icon(
        imageVector = Icons.Outlined.DirectionsBus,
        contentDescription = null,
        modifier = Modifier.size(64.dp),
        tint = MaterialTheme.colors.primary,
    )
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = MaterialTheme.colors.primary,
        text = stringResource(R.string.title)
    )
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        textAlign = TextAlign.Center,
        fontSize = 12.sp,
        color = MaterialTheme.colors.onSurfaceVariant,
        text = stringResource(R.string.subtitle)
    )
}
