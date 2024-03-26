package co.mesquita.labs.bustime.presentation.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import co.mesquita.labs.bustime.R
import co.mesquita.labs.bustime.components.SearchTextField
import com.google.android.horologist.compose.layout.fillMaxRectangle

@Composable
fun SearchScreen(
    isLoading: Boolean,
    navController: NavController,
    onSearchButtonClick: (NavController, String) -> Unit
) {
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
            SearchTextField(navController) { navController, stopNumber ->
                onSearchButtonClick(navController, stopNumber)
            }
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
