package agh.ryszard.blazej.heartbeat_app.data

import kotlinx.serialization.Serializable

@Serializable
data class Measurement (
    val mac: String,
    val startHour: Int,
    val startMinute: Int,
    val endHour: Int,
    val endMinute: Int
)