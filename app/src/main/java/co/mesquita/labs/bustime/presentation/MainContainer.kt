package co.mesquita.labs.bustime.presentation

import androidx.compose.runtime.Composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import co.mesquita.labs.bustime.presentation.nav.AppNavigation
import com.google.android.horologist.compose.layout.AppScaffold

@Composable
fun MainContainer() {
    val navController = rememberSwipeDismissableNavController()

    AppScaffold {
        AppNavigation(navController)
    }
}
