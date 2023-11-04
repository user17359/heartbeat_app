package agh.ryszard.blazej.heartbeat_app.ui.screens

import agh.ryszard.blazej.heartbeat_app.HeartbeatScreen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

enum class SensorState{
    Empty,
    Waiting,
    Measuring
}

@Composable
fun SensorMenuScreen(navController: NavHostController, mac: String) {
    var sensorState =  SensorState.Measuring
    Scaffold (containerColor = MaterialTheme.colorScheme.surface) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
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
                        text = "Movesense 543534532",
                        style = MaterialTheme.typography.titleLarge
                    )

                    IconButton(onClick = { onBack(navController) }) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = "Back arrow")
                    }
                }
                Divider()
                Text(
                    text = "ECG test",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .padding(top = 12.dp, bottom = 12.dp),
                )
            }
            Button(
                onClick = { onStartMeasurement(navController, mac) },
                modifier = Modifier
                    .padding(36.dp, 12.dp, 36.dp, 18.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                Text(text = "Start measurement")
            }
            if(sensorState == SensorState.Empty){

            }
            else if(sensorState == SensorState.Waiting){
                Text(
                    //TODO: get actual data
                    text = "Starting at 14:44",
                    modifier = Modifier
                        .align(Alignment.Center),
                    style = MaterialTheme.typography.displaySmall
                )
            }
            else if(sensorState == SensorState.Measuring){
                // TODO plotting of current signal
            }
        }
    }
}

private fun onBack(navController: NavHostController) {
    navController.navigate(HeartbeatScreen.GatewayMenu.name)
}

private fun onStartMeasurement(navController: NavHostController, mac: String) {
    navController.navigate("${HeartbeatScreen.NewMeasurement.name}/$mac")
}