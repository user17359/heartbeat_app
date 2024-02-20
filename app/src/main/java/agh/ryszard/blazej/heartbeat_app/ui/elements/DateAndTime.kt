package agh.ryszard.blazej.heartbeat_app.ui.elements

import agh.ryszard.blazej.heartbeat_app.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateAndTime (time: TimePickerState, onClickTime: () -> Unit, isTimeSelected: Boolean,
                 date: DatePickerState, onClickDate: () -> Unit, isDateSelected: Boolean,
                 labelPrefix: String){

    Row (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        DatePickerTextField(
            onClick = onClickDate,
            modifier = Modifier
                .fillMaxWidth(fraction = 0.55f),
            isDateSelected = isDateSelected,
            date = date,
            leadingIcon = @Composable {
            Icon(
                painterResource(R.drawable.baseline_date_range_24),
                contentDescription = "Calendar"
            )
        },
            label = "$labelPrefix date"
        )
        TimepickerTextField(
            onClick = onClickTime,
            modifier = Modifier
                .fillMaxWidth(fraction = 0.9f),
            isTimeSelected = isTimeSelected,
            leadingIcon = @Composable {
                Icon(
                    painterResource(R.drawable.schedule_24px),
                    contentDescription = "Clock"
                )
            },
            time = time,
            label = "$labelPrefix time"
        )
    }
}