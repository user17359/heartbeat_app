package agh.ryszard.blazej.heartbeat_app

import agh.ryszard.blazej.heartbeat_app.ui.theme.HeartbeatTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            HeartbeatTheme {
                HeartbeatApp()
            }
        }
    }
}
