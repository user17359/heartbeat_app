package agh.ryszard.blazej.heartbeat_app.data

import kotlinx.serialization.Serializable

@Serializable
data class ProgressStatus (
    val state: String,
    val label: String,
    val startTime: String
)