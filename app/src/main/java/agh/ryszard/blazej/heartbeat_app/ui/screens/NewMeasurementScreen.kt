package agh.ryszard.blazej.heartbeat_app.ui.screens

import agh.ryszard.blazej.heartbeat_app.HeartbeatScreen
import agh.ryszard.blazej.heartbeat_app.R
import agh.ryszard.blazej.heartbeat_app.dataClasses.jsonSerializables.Measurement
import agh.ryszard.blazej.heartbeat_app.dataClasses.supportedSensors.SensorSettings
import agh.ryszard.blazej.heartbeat_app.dataClasses.supportedSensors.SensorToggleParameter
import agh.ryszard.blazej.heartbeat_app.ui.elements.AlertDialogTemplate
import agh.ryszard.blazej.heartbeat_app.ui.elements.TimePickerDialog
import agh.ryszard.blazej.heartbeat_app.ui.elements.TimepickerTextField
import agh.ryszard.blazej.heartbeat_app.viewmodel.ScanViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

enum class Timepicker {
    Start,
    End,
    None
}

@Composable
@ExperimentalMaterial3Api
fun NewMeasurementScreen(navController: NavHostController,
                         scanViewModel: ScanViewModel,
                         mac: String) {

    val coroutineScope = rememberCoroutineScope()

    // settings from current sensor
    val device = scanViewModel.getDevice(mac)
    val sensorSettings: SensorSettings = scanViewModel.getSettings(device)

    // toggle state of sensor units eg. "ECG": false
    val unitsToggle = remember { mutableStateMapOf<String, Boolean>() }
    // expanded state of parameter menus per their unique ID
    val expanded = remember { mutableStateMapOf<Int, Boolean>() }
    // selected option of parameter menu per their unique ID
    val selectedOptionText = remember { mutableStateMapOf<Int, String>() }

    sensorSettings.units.forEach { unit ->
        unitsToggle[unit.name] = false
        unit.parameters.forEach { parameter ->
            if(parameter is SensorToggleParameter) {
                expanded[parameter.uniqueId] = false
                selectedOptionText[parameter.uniqueId] = parameter.options[0]
            }
        }
    }

    var label by remember { mutableStateOf("") }
    val startTime = rememberTimePickerState(is24Hour = true)
    val endTime = rememberTimePickerState(is24Hour = true)
    val showTimePicker = remember { mutableStateOf(Timepicker.None) }
    val isStartTimeSelected = remember { mutableStateOf(false) }
    val isEndTimeSelected = remember { mutableStateOf(false) }
    val showDialog = remember { mutableStateOf(false) }
    val delayed = remember { mutableStateOf(true) }

    Scaffold(containerColor = MaterialTheme.colorScheme.surface) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (showDialog.value) {
                AlertDialogTemplate(
                    onDismissRequest = { showDialog.value = false },
                    onConfirmation = { showDialog.value = false; onCancel(navController, mac) },
                    dialogTitle = "Are you sure to cancel?",
                    dialogText = "All data will be lost"
                )
            }
            if (showTimePicker.value != Timepicker.None) {
                TimePickerDialog(
                    onDismiss =
                    {
                        if(showTimePicker.value == Timepicker.Start)
                            isStartTimeSelected.value = true
                        else
                            isEndTimeSelected.value = true
                        showTimePicker.value = Timepicker.None
                    },
                    timePickerState = if(showTimePicker.value == Timepicker.Start) startTime else endTime
                )
            }
            Column(
                modifier = Modifier
                    .padding(36.dp, 18.dp, 36.dp, 70.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "New measurement",
                        style = MaterialTheme.typography.titleLarge
                    )
                    IconButton(onClick = {
                        showDialog.value = true
                    }
                    ) {
                        Icon(Icons.Rounded.Close, contentDescription = "X sign")
                    }
                }
                Text(
                    text = "Data",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(bottom = 12.dp)
                )
                OutlinedTextField(
                    value = label,
                    onValueChange = { label = it },
                    label = { Text("Label") },
                    leadingIcon = @Composable {
                        Icon(
                            painterResource(R.drawable.label_24px),
                            contentDescription = "Label"
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = "Delayed",
                        style = MaterialTheme.typography.labelLarge
                    )
                    Switch(
                        checked = delayed.value,
                        onCheckedChange = {
                            delayed.value = it
                        }
                    )
                }
                if(delayed.value) {
                    TimepickerTextField(
                        onClick = { showTimePicker.value = Timepicker.Start },
                        modifier = Modifier
                            .fillMaxWidth(),
                        isTimeSelected = isStartTimeSelected.value,
                        leadingIcon = @Composable {
                            Icon(
                                painterResource(R.drawable.schedule_24px),
                                contentDescription = "Clock"
                            )
                        },
                        time = startTime,
                        label = "Start time"
                    )
                }
                TimepickerTextField(
                    onClick = { showTimePicker.value = Timepicker.End },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = if (delayed.value) 8.dp else 0.dp),
                    isTimeSelected = isEndTimeSelected.value,
                    leadingIcon = @Composable {
                        Icon(
                            painterResource(R.drawable.schedule_24px),
                            contentDescription = "Clock"
                        )
                    },
                    time = endTime,
                    label = "End time"
                )
                Text(
                    text = "Sensor settings",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(top = 12.dp, bottom = 4.dp)
                )
                sensorSettings.units.forEach { unit ->
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                    ){
                        CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                            Checkbox(
                                checked = unitsToggle[unit.name]!!,
                                onCheckedChange = { unitsToggle[unit.name] = it },
                                modifier = Modifier
                                    .padding(bottom = 12.dp, top = 12.dp, end = 12.dp)
                                    .defaultMinSize(0.dp, 0.dp)
                            )
                        }
                        Text(
                            text = unit.name,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                    if(unitsToggle[unit.name]!!){
                        unit.parameters.forEach { parameter ->
                            if (parameter is SensorToggleParameter) {
                                ExposedDropdownMenuBox(
                                    expanded = expanded[parameter.uniqueId]!!,
                                    onExpandedChange = {
                                        expanded[parameter.uniqueId] =
                                            !expanded[parameter.uniqueId]!!
                                    },
                                    modifier = Modifier
                                        .padding(bottom = 12.dp, start = 12.dp),
                                ) {
                                    OutlinedTextField(
                                        modifier = Modifier
                                            .menuAnchor(),
                                        readOnly = true,
                                        value = selectedOptionText[parameter.uniqueId]!!,
                                        onValueChange = {},
                                        label = { Text(parameter.name) },
                                        trailingIcon = {
                                            ExposedDropdownMenuDefaults.TrailingIcon(
                                                expanded = expanded[parameter.uniqueId]!!
                                            )
                                        },
                                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                                    )
                                    ExposedDropdownMenu(
                                        expanded = expanded[parameter.uniqueId]!!,
                                        onDismissRequest = { expanded[parameter.uniqueId] = false },
                                    ) {
                                        parameter.options.forEach { selectionOption ->
                                            DropdownMenuItem(
                                                text = { Text(selectionOption) },
                                                onClick = {
                                                    selectedOptionText[parameter.uniqueId] =
                                                        selectionOption
                                                    expanded[parameter.uniqueId] = false
                                                },
                                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Button(
                onClick = {
                    val chosenUnits: MutableList<HashMap<String, String>> = mutableListOf()
                    unitsToggle.forEach{ unit ->
                        if(unit.value) {
                            chosenUnits.add(HashMap())

                            val lastUnit = sensorSettings.units.last { it.name == unit.key }
                            lastUnit.parameters.forEach { parameter ->
                                chosenUnits.last()[parameter.encodedName] = selectedOptionText[parameter.uniqueId]!!
                            }
                            chosenUnits.last()["name"] = lastUnit.encodedName
                        }
                    }
                    coroutineScope.launch {
                        onSave(navController, scanViewModel,
                            Measurement(
                                mac,
                                sensorSettings.tag.encodedName,
                                label,
                                startTime.hour,
                                startTime.minute,
                                endTime.hour,
                                endTime.minute,
                                chosenUnits.toList(),
                                nextDayStart = false,
                                nextDayEnd = false
                            ), delayed.value
                        )
                    }
                },
                modifier = Modifier
                    .padding(36.dp, 12.dp, 36.dp, 18.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                Text(text = stringResource(R.string.save))
            }
        }
    }
}

private fun onCancel(navController: NavHostController, mac: String) {
    navController.navigate("${HeartbeatScreen.SensorMenu.name}/$mac")
}

private suspend fun onSave(navController: NavHostController,
                           viewModel: ScanViewModel,
                           measurement: Measurement,
                           delayed: Boolean) {
    viewModel.addMeasurement(measurement, delayed)
    navController.navigate("${HeartbeatScreen.MeasurementLoading.name}/${measurement.mac}")
}