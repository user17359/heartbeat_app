package agh.ryszard.blazej.heartbeat_app.ui.screens

import agh.ryszard.blazej.heartbeat_app.HeartbeatScreen
import agh.ryszard.blazej.heartbeat_app.R
import agh.ryszard.blazej.heartbeat_app.mockData.loadConncectedSensors
import agh.ryszard.blazej.heartbeat_app.ui.elements.AlertDialogTemplate
import agh.ryszard.blazej.heartbeat_app.ui.elements.BtDeviceCard
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import java.util.concurrent.TimeUnit

@Preview
@Composable
fun PreviewGatewayMenu(){
    GatewayMenuScreen(navController = rememberNavController())
}

@Composable
fun GatewayMenuScreen(navController: NavHostController) {
    val showDialog = remember { mutableStateOf(false) }
    Scaffold (containerColor = MaterialTheme.colorScheme.surface) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if(showDialog.value) {
                AlertDialogTemplate(
                    onDismissRequest = {showDialog.value = false},
                    onConfirmation = { showDialog.value = false; onDisconnect(navController) },
                    dialogTitle = "Are you sure to disconnect?",
                    dialogText = "lorem ipsum sit dolar amet"
                )
            }
            Column(
                modifier = Modifier
                    .padding(36.dp, 18.dp, 36.dp, 12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Heartbeat 5435",
                        style = MaterialTheme.typography.headlineMedium
                    )

                    IconButton(
                        onClick = {
                            showDialog.value = true
                        }
                    ) {
                        Icon(Icons.Rounded.Close, contentDescription = "X mark")
                    }
                }
                Divider()
                Text(
                    text = "Connected sensors",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .padding(top = 12.dp, bottom = 12.dp),
                )
                loadConncectedSensors().forEach { sensor ->
                    BtDeviceCard(
                        icon = painterResource(R.drawable.ecg_heart_24px),
                        name = sensor.name,
                        macAddress = sensor.macAdress,
                        onClick = { onSensorClick(navController) }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            Row (
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(36.dp, 12.dp, 36.dp, 18.dp)
            ){
                OutlinedButton(
                    onClick = { onAddEvent(navController) },
                    modifier = Modifier
                        .weight(1.5f)
                ) {
                    Text(text = stringResource(R.string.add_event))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { onAddSensor(navController) },
                    modifier = Modifier
                        .weight(2.0f)
                ) {
                    Text(text = stringResource(R.string.add_sensor))
                }
            }
        }
    }
}

private fun onDisconnect(navController: NavHostController) {
    navController.navigate(HeartbeatScreen.SensorSelection.name)
}
private fun onSensorClick(navController: NavHostController) {
    try {
        // sleep for animation time
        TimeUnit.MILLISECONDS.sleep(250)
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }
    navController.navigate(HeartbeatScreen.SensorMenu.name)
}
private fun onAddEvent(navController: NavHostController) {
    try {
        // sleep for animation time
        TimeUnit.MILLISECONDS.sleep(250)
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }
    navController.navigate(HeartbeatScreen.DiaryEntry.name)
}
private fun onAddSensor(navController: NavHostController) {
    try {
        // sleep for animation time
        TimeUnit.MILLISECONDS.sleep(250)
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }
    navController.navigate(HeartbeatScreen.SensorSelection.name)
}