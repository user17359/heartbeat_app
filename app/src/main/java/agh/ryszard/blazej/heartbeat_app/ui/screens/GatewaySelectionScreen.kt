package agh.ryszard.blazej.heartbeat_app.ui.screens

import agh.ryszard.blazej.heartbeat_app.HeartbeatScreen
import agh.ryszard.blazej.heartbeat_app.mockData.loadGateways
import agh.ryszard.blazej.heartbeat_app.ui.elements.BtDeviceCard
import agh.ryszard.blazej.heartbeat_app.ui.elements.CornerDecoration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun GatewaySelectionScreen(
    navController: NavHostController
) {
    Scaffold (containerColor = MaterialTheme.colorScheme.surface) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            CornerDecoration(
                modifier = Modifier
                    .align(Alignment.TopStart)
            )
            CornerDecoration(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .rotate(180.0f)
            )
            Column(
                modifier = Modifier
                    .padding(
                        start = 50.dp,
                        end = 50.dp,
                    )
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 20.dp),
                    text = "Select gateway to connect",
                    style = MaterialTheme.typography.titleLarge
                )
                loadGateways().forEach { gateway ->
                    BtDeviceCard(
                        icon = Icons.Rounded.FavoriteBorder,
                        name = gateway.name,
                        macAddress = gateway.macAdress,
                        onClick = { onDeviceClick(navController) }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

private fun onDeviceClick(navController: NavHostController) {
    navController.navigate(HeartbeatScreen.GatewayLoading.name)
}