package co.mesquita.labs.bustime.ui

import androidx.compose.runtime.Composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import co.mesquita.labs.bustime.ui.nav.AppNavigation
import com.google.android.horologist.compose.layout.AppScaffold

@Composable
fun MainContainer() {
    val navController = rememberSwipeDismissableNavController()

    AppScaffold {
        AppNavigation(navController)
    }
}
