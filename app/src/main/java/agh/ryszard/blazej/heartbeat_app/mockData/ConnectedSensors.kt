package agh.ryszard.blazej.heartbeat_app.mockData

import agh.ryszard.blazej.heartbeat_app.dataClasses.jsonParsing.BtDevice

fun loadConncectedSensors(): List<BtDevice> {
    return listOf<BtDevice>(
        BtDevice("Movesense 543534532", "12:12:12:12:DD"),
        BtDevice("Movesense 645645645", "12:12:12:12:CC"),
    )
}