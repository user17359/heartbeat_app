package agh.ryszard.blazej.heartbeat_app.ui.screens

import agh.ryszard.blazej.heartbeat_app.mockData.loadGateways
import agh.ryszard.blazej.heartbeat_app.ui.elements.BtDeviceCard
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GatewaySelectionScreen() {
    Scaffold (containerColor = MaterialTheme.colorScheme.surface){ innerPadding ->
        Column(
            modifier = Modifier
                .padding(
                    start = 50.dp,
                    end = 50.dp,
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(
                modifier = Modifier.padding(bottom = 20.dp),
                text = "Select gateway to connect",
                style = MaterialTheme.typography.titleLarge
            )
            loadGateways().forEach{ gateway ->
                BtDeviceCard(icon = Icons.Rounded.FavoriteBorder, name = gateway.name, macAddress = gateway.macAdress)
            }
        }
    }
}