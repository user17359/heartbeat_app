package agh.ryszard.blazej.heartbeat_app

import agh.ryszard.blazej.heartbeat_app.ui.screens.DiaryEntryScreen
import agh.ryszard.blazej.heartbeat_app.ui.screens.GatewayLoadingScreen
import agh.ryszard.blazej.heartbeat_app.ui.screens.GatewayMenuScreen
import agh.ryszard.blazej.heartbeat_app.ui.screens.GatewaySelectionScreen
import agh.ryszard.blazej.heartbeat_app.ui.screens.NewMeasurementScreen
import agh.ryszard.blazej.heartbeat_app.ui.screens.SensorLoadingScreen
import agh.ryszard.blazej.heartbeat_app.ui.screens.SensorMenuScreen
import agh.ryszard.blazej.heartbeat_app.ui.screens.SensorSelectionScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

enum class HeartbeatScreen {
    GatewaySelection,
    GatewayLoading,
    GatewayMenu,
    SensorSelection,
    SensorLoading,
    DiaryEntry,
    SensorMenu,
    NewMeasurement
}

@Composable
fun HeartbeatApp(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = HeartbeatScreen.GatewaySelection.name) {
        composable(HeartbeatScreen.GatewaySelection.name) { GatewaySelectionScreen(navController) }
        composable(HeartbeatScreen.GatewayLoading.name) { GatewayLoadingScreen() }
        composable(HeartbeatScreen.GatewayMenu.name) { GatewayMenuScreen() }
        composable(HeartbeatScreen.SensorSelection.name) { SensorSelectionScreen() }
        composable(HeartbeatScreen.SensorLoading.name) { SensorLoadingScreen() }
        composable(HeartbeatScreen.SensorMenu.name) { SensorMenuScreen() }
        composable(HeartbeatScreen.DiaryEntry.name) { DiaryEntryScreen() }
        composable(HeartbeatScreen.NewMeasurement.name) { NewMeasurementScreen() }
    }
}