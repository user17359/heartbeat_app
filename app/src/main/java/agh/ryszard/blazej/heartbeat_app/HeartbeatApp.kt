package agh.ryszard.blazej.heartbeat_app

import agh.ryszard.blazej.heartbeat_app.ui.screens.DiaryEntryScreen
import agh.ryszard.blazej.heartbeat_app.ui.screens.GatewayLoadingScreen
import agh.ryszard.blazej.heartbeat_app.ui.screens.GatewayMenuScreen
import agh.ryszard.blazej.heartbeat_app.ui.screens.GatewaySelectionScreen
import agh.ryszard.blazej.heartbeat_app.ui.screens.NewMeasurementScreen
import agh.ryszard.blazej.heartbeat_app.ui.screens.SensorLoadingScreen
import agh.ryszard.blazej.heartbeat_app.ui.screens.SensorMenuScreen
import agh.ryszard.blazej.heartbeat_app.ui.screens.SensorSelectionScreen
import androidx.compose.material3.ExperimentalMaterial3Api
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
@ExperimentalMaterial3Api
fun HeartbeatApp(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = HeartbeatScreen.GatewaySelection.name) {
        composable(HeartbeatScreen.GatewaySelection.name) { GatewaySelectionScreen(navController) }
        composable(HeartbeatScreen.GatewayLoading.name) { GatewayLoadingScreen(navController) }
        composable(HeartbeatScreen.GatewayMenu.name) { GatewayMenuScreen(navController) }
        composable(HeartbeatScreen.SensorSelection.name) { SensorSelectionScreen(navController) }
        composable(HeartbeatScreen.SensorLoading.name) { SensorLoadingScreen(navController) }
        composable(HeartbeatScreen.SensorMenu.name) { SensorMenuScreen(navController) }
        composable(HeartbeatScreen.DiaryEntry.name) { DiaryEntryScreen(navController) }
        composable(HeartbeatScreen.NewMeasurement.name) { NewMeasurementScreen() }
    }
}