package agh.ryszard.blazej.heartbeat_app.viewmodel

import agh.ryszard.blazej.heartbeat_app.data.BtDevice
import android.annotation.SuppressLint
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Time after we stop scanning
private const val SCAN_PERIOD: Long = 60000

class ScanViewModel: ViewModel() {

    val listOfDevices = MutableLiveData<Set<BtDevice>>()

    @SuppressLint("MissingPermission")
    fun scanLeDevice(bluetoothManager: BluetoothManager) {
        listOfDevices.value = setOf()
        val bluetoothLeScanner: BluetoothLeScanner? =
            bluetoothManager.adapter.bluetoothLeScanner

        if (bluetoothLeScanner != null) {
            bluetoothLeScanner.startScan(leScanCallback)
            val scope = CoroutineScope(Dispatchers.Main)
            scope.launch { stopScan(bluetoothLeScanner) }
        }
    }

    @SuppressLint("MissingPermission")
    private suspend fun stopScan(bluetoothLeScanner: BluetoothLeScanner) {
        delay(SCAN_PERIOD)
        bluetoothLeScanner.stopScan(leScanCallback)
    }

    private val leScanCallback: ScanCallback = object : ScanCallback() {
        @SuppressLint("MissingPermission")
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            if(result.device.name != null && result.device.name.contains("Heartbeat")) {
                listOfDevices.value = listOfDevices.value?.toMutableList()?.apply {
                    add(BtDevice(result.device.name, result.device.address))
                }?.toSet()
            }
        }
    }
}
