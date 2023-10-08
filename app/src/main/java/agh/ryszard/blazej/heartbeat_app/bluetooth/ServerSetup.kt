package agh.ryszard.blazej.heartbeat_app.bluetooth

import android.Manifest
import android.app.Application
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattServerCallback
import android.bluetooth.BluetoothGattService
import android.bluetooth.BluetoothManager
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import java.util.UUID

val SERVICE_UUID: UUID = UUID.fromString("af280fcd-23a4-4f7d-ab33-b6f92cf25719")
val MESSAGE_UUID: UUID = UUID.fromString("402b935d-15c7-4835-8856-bfc52db8e2b0")

fun setupGattServer(app: Application, bluetoothManager: BluetoothManager) {
    val gattServerCallback = GattServerCallback()

    if (ActivityCompat.checkSelfPermission(
            app,
            Manifest.permission.BLUETOOTH_CONNECT
        ) != PackageManager.PERMISSION_GRANTED
    ) {

        return
    }
    bluetoothManager.openGattServer(
        app,
        gattServerCallback
    ).apply {
        addService(setupGattService())
    }
}

private fun setupGattService(): BluetoothGattService {
    val service = BluetoothGattService(SERVICE_UUID, BluetoothGattService.SERVICE_TYPE_PRIMARY)

    val messageCharacteristic = BluetoothGattCharacteristic(
        MESSAGE_UUID,
        BluetoothGattCharacteristic.PROPERTY_WRITE,
        BluetoothGattCharacteristic.PERMISSION_WRITE,
    )
    messageCharacteristic.

    service.addCharacteristic(messageCharacteristic)
    return service
}

private class GattServerCallback : BluetoothGattServerCallback() {

    override fun onConnectionStateChange(device: BluetoothDevice, status: Int, newState: Int) {

    }

    override fun onCharacteristicWriteRequest(
        device: BluetoothDevice,
        requestId: Int,
        characteristic: BluetoothGattCharacteristic,
        preparedWrite: Boolean,
        responseNeeded: Boolean,
        offset: Int,
        value: ByteArray?
    ) {

    }
}