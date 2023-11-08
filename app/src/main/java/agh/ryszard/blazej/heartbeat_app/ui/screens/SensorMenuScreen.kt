package agh.ryszard.blazej.heartbeat_app.ui.screens

import agh.ryszard.blazej.heartbeat_app.HeartbeatScreen
import agh.ryszard.blazej.heartbeat_app.viewmodel.ScanViewModel
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.scroll.ChartScrollSpec
import com.patrykandpatrick.vico.compose.m3.style.m3ChartStyle
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.scroll.AutoScrollCondition
import com.patrykandpatrick.vico.core.scroll.InitialScroll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

enum class SensorState(val jsonCode: String){
    Empty("empty"),
    Scheduled("scheduled"),
    Measuring("measuring");

    companion object {
        fun fromString(value: String) = SensorState.values().first { it.jsonCode == value }
    }
}

@Composable
fun SensorMenuScreen(
    navController: NavHostController,
    scanViewModel: ScanViewModel,
    mac: String,
    name: String) {

    val sensorState = scanViewModel.measurementState.observeAsState()

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            scanViewModel.checkStatus()
        }
    }

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
                        text = name,
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
                onClick = { onStartMeasurement(navController, mac, name) },
                modifier = Modifier
                    .padding(36.dp, 12.dp, 36.dp, 18.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                Text(text = "Start measurement")
            }
            /*if(sensorState.value == SensorState.Empty){

            }*/
            if(sensorState.value == SensorState.Scheduled){
                Text(
                    //TODO: get actual data
                    text = "Starting at 14:44",
                    modifier = Modifier
                        .align(Alignment.Center),
                    style = MaterialTheme.typography.displaySmall
                )
            }
            else if(sensorState.value == SensorState.Measuring){
                Column (
                    modifier = Modifier
                        .padding(18.dp, 0.dp, 24.dp, 0.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    ProvideChartStyle (m3ChartStyle()) {
                        Chart(
                            chart = lineChart(
                                lines = listOf(
                                    LineChart.LineSpec(lineBackgroundShader = null, lineColor = MaterialTheme.colorScheme.primary.toArgb()),
                                    LineChart.LineSpec(lineBackgroundShader = null, lineColor = Color(0xFFCC7722).toArgb()),
                                    LineChart.LineSpec(lineBackgroundShader = null, lineColor = MaterialTheme.colorScheme.tertiary.toArgb())
                                )
                            ),
                            chartModelProducer = scanViewModel.chartEntryModelProducer,
                            startAxis = rememberStartAxis(),
                            bottomAxis = rememberBottomAxis(
                                itemPlacer = AxisItemPlacer.Horizontal.default(
                                    spacing = 25
                                )
                            ),
                            chartScrollSpec = ChartScrollSpec(
                                true,
                                InitialScroll.End,
                                AutoScrollCondition.OnModelSizeIncreased,
                                tween(100)
                            ),
                            runInitialAnimation = false,
                            diffAnimationSpec = tween(0)
                        )
                    }
                }
            }
        }
    }
}

private fun onBack(navController: NavHostController) {
    navController.navigate(HeartbeatScreen.GatewayMenu.name)
}

private fun onStartMeasurement(navController: NavHostController, mac: String, name: String) {
    navController.navigate("${HeartbeatScreen.NewMeasurement.name}/$mac/$name")
}