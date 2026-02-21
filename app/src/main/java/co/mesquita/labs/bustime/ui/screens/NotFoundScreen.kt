package co.mesquita.labs.bustime.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.wear.compose.material.MaterialTheme
import co.mesquita.labs.bustime.R
import co.mesquita.labs.bustime.ui.components.EmptyFragment

@Composable
fun NotFoundScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        EmptyFragment(stringResource(R.string.empty_table))
    }
}
