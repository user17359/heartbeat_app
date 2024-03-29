package agh.ryszard.blazej.heartbeat_app.ui.screens

import agh.ryszard.blazej.heartbeat_app.HeartbeatScreen
import agh.ryszard.blazej.heartbeat_app.R
import agh.ryszard.blazej.heartbeat_app.ui.elements.CornerDecoration
import agh.ryszard.blazej.heartbeat_app.viewmodel.ScanViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.juul.kable.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

@Composable
fun MeasurementLoadingScreen(
    navHostController: NavHostController,
    scanViewModel: ScanViewModel,
    mac: String
) {
    val connectionState = scanViewModel.connectionState.observeAsState()
    val reconnectState = scanViewModel.reconnectState.observeAsState()

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            scanViewModel.timedReconnect(5000)
        }
    }

    LaunchedEffect(connectionState.value) {
        if(connectionState.value is State.Connected && reconnectState.value == true) {
            onLoadingEnd(navHostController)
        }
    }

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
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.ecg_animation_v2))
            Column (
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                LottieAnimation(
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                )
                Text(
                    text = "Planning measurement...",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(16.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

private suspend fun onLoadingEnd(navController: NavHostController) = coroutineScope{
    navController.navigate(HeartbeatScreen.GatewayMenu.name)
}