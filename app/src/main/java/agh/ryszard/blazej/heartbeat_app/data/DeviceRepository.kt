package agh.ryszard.blazej.heartbeat_app.data

import agh.ryszard.blazej.heartbeat_app.dataClasses.jsonSerializables.BtDevice

class DeviceRepository {

    val deviceList = mutableListOf<BtDevice>()

    fun addDevice(device: BtDevice) {
        deviceList.add(device)
    }
}