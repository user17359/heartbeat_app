package agh.ryszard.blazej.heartbeat_app.mockData

import agh.ryszard.blazej.heartbeat_app.data.BtDevice

fun loadGateways(): List<BtDevice> {
    return listOf<BtDevice>(
        BtDevice("Heartbeat 2222", "12:12:12:12:DD"),
        BtDevice("Heartbeat 3333", "12:12:12:12:CC"),
        BtDevice("Heartbeat 4444", "12:12:12:12:BB")
    )
}
