package co.mesquita.labs.bustime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import co.mesquita.labs.bustime.ui.MainContainer
import co.mesquita.labs.bustime.ui.theme.BusTimeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setContent {
            BusTimeTheme {
                MainContainer()
            }
        }
    }
}
