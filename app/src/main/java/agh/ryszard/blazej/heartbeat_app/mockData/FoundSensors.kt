package agh.ryszard.blazej.heartbeat_app.mockData

import agh.ryszard.blazej.heartbeat_app.data.BtDevice

fun loadFoundSensors(): List<BtDevice> {
    return listOf<BtDevice>(
        BtDevice("Movesense 543534534", "12:12:12:12:DD"),
        BtDevice("Movesense 867867643", "12:12:12:12:CC"),
        BtDevice("Movesense 647568512", "12:12:12:12:CC"),
    )
}