package agh.ryszard.blazej.heartbeat_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import agh.ryszard.blazej.heartbeat_app.ui.theme.Heartbeat_appTheme
import android.bluetooth.BluetoothManager
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import agh.ryszard.blazej.heartbeat_app.bluetooth.startAdvertising

class MainActivity : ComponentActivity() {

    private lateinit var bluetoothManager: BluetoothManager
    private val activity = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        setContent {
            Heartbeat_appTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box (contentAlignment = Alignment.Center)
                    {
                        ButtonWithBorder("Search for gateway") { startAdvertising(bluetoothManager, activity) }
                    }
                }
            }
        }
    }
}

@Composable
fun ButtonWithBorder(content: String, action: () -> Unit) {
    Button(
        onClick = action,
        border = BorderStroke(1.dp, Color.Red),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
    ) {
        Text(text = content, color = Color.DarkGray)
    }
}
