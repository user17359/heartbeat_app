package agh.ryszard.blazej.heartbeat_app.ui.screens

import agh.ryszard.blazej.heartbeat_app.R
import agh.ryszard.blazej.heartbeat_app.ui.elements.TimePickerDialog
import agh.ryszard.blazej.heartbeat_app.ui.elements.TimepickerTextField
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBoxk
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview
@ExperimentalMaterial3Api
fun DiaryEntryScreen() {
    var label by remember { mutableStateOf("") }
    val time = rememberTimePickerState(is24Hour = true)
    var description by remember { mutableStateOf("") }
    val showTimePicker = remember { mutableStateOf(false) }
    var isTimeSelected = false

    Scaffold (containerColor = MaterialTheme.colorScheme.surface) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (showTimePicker.value) {
                TimePickerDialog(onDismiss =
                {
                    showTimePicker.value = false
                    isTimeSelected = true
                },
                    timePickerState = time)
            }
            Column(
                modifier = Modifier
                    .padding(36.dp, 18.dp, 36.dp, 12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Adding event",
                        style = MaterialTheme.typography.titleLarge
                    )

                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Rounded.Close, contentDescription = "X sign")
                    }
                }
                OutlinedTextField(
                    value = label,
                    onValueChange = {label = it},
                    label = { Text("Label") },
                    leadingIcon = @Composable { Icon(Icons.Rounded.AccountBox, contentDescription = "")},
                    modifier = Modifier
                        .fillMaxWidth()
                )
                TimepickerTextField(
                    onClick = { showTimePicker.value = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    isTimeSelected = isTimeSelected,
                    time = time
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = {description = it},
                    label = { Text("Description") },
                    minLines = 8,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
            }
            Button(
                onClick = { /*TODO*/ },
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