package co.mesquita.labs.bustime.ui

import androidx.compose.runtime.Composable
import androidx.wear.compose.material3.AppScaffold
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import co.mesquita.labs.bustime.ui.nav.AppNavigation
import com.google.android.horologist.compose.ambient.AmbientAware

@Composable
fun MainContainer() {
    val navController = rememberSwipeDismissableNavController()

    AmbientAware { ambientStateUpdate ->
        AppScaffold {
            AppNavigation(
                navController = navController,
                isAmbient = ambientStateUpdate.isAmbient
            )
        }
    }
}
