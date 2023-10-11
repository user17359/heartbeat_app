package agh.ryszard.blazej.heartbeat_app.ui.elements

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
@ExperimentalMaterial3Api
fun TimepickerTextField (onClick: () -> Unit, modifier: Modifier = Modifier, isTimeSelected: Boolean, time: TimePickerState){
    OutlinedTextField(
        value = if(isTimeSelected)
            time.hour.toString() + ":" + time.minute.toString()
        else
            "",
        enabled = false,
        onValueChange = {},
        label = { Text("Time") },
        leadingIcon = @Composable { Icon(Icons.Rounded.FavoriteBorder, contentDescription = "") },
        // workaround to create text field + time picker
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = colorScheme.onSurface,
            disabledBorderColor = colorScheme.outline,
            disabledLeadingIconColor = colorScheme.onSurfaceVariant,
            disabledTrailingIconColor = colorScheme.onSurfaceVariant,
            disabledLabelColor = colorScheme.onSurfaceVariant,
            disabledPlaceholderColor = colorScheme.onSurfaceVariant,
        ),
        modifier = modifier
            .noRippleClickable {
                onClick()
            }
    )
}