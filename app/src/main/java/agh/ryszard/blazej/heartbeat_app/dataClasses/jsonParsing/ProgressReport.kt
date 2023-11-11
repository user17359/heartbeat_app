package agh.ryszard.blazej.heartbeat_app.dataClasses.jsonParsing

import kotlinx.serialization.Serializable

@Serializable
data class ProgressReport (
    val state: String,
    val info: String
)