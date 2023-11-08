package agh.ryszard.blazej.heartbeat_app.data

import kotlinx.serialization.Serializable

@Serializable
data class ProgressReport (
    val state: String,
    val info: String
)