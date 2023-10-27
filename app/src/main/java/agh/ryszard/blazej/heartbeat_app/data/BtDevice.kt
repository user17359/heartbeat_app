package agh.ryszard.blazej.heartbeat_app.data

import kotlinx.serialization.Serializable


@Serializable
data class BtDevice (
    val name: String = "",
    val mac: String = ""
)