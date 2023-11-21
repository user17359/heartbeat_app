package agh.ryszard.blazej.heartbeat_app.dataClasses.supportedSensors.Movesense

import agh.ryszard.blazej.heartbeat_app.dataClasses.jsonSerializables.BtDevice

fun movesenseValidator(device: BtDevice): Boolean{
    val regex = Regex("Movesense")
    return regex.containsMatchIn(device.name)
}