package agh.ryszard.blazej.heartbeat_app.dataClasses.jsonParsing

import kotlinx.serialization.Serializable

@Serializable
data class DiaryEntry (
    val label: String,
    val hour: Int,
    val minute: Int,
    val description: String
)