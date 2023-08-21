package agh.ryszard.blazej.heartbeat_app.bluetooth

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothManager
import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseData
import android.bluetooth.le.AdvertiseSettings
import android.bluetooth.le.BluetoothLeAdvertiser
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.ParcelUuid
import android.util.Log
import androidx.core.app.ActivityCompat
import java.util.UUID

val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
    arrayOf(
        Manifest.permission.BLUETOOTH_ADVERTISE,
        Manifest.permission.BLUETOOTH_CONNECT,
        Manifest.permission.ACCESS_COARSE_LOCATION)
} else {
    arrayOf()
}

private val advertiseCallback = object : AdvertiseCallback() {
    override fun onStartSuccess(settingsInEffect: AdvertiseSettings) {
        Log.i(ContentValues.TAG, "LE Advertise Started.")
    }

    override fun onStartFailure(errorCode: Int) {
        Log.w(ContentValues.TAG, "LE Advertise Failed: $errorCode")
    }
}

@SuppressLint("MissingPermission")
fun startAdvertising(bluetoothManager: BluetoothManager, activity: Activity) {
    val bluetoothLeAdvertiser: BluetoothLeAdvertiser? =
        bluetoothManager.adapter.bluetoothLeAdvertiser

    bluetoothLeAdvertiser?.let {
        //settings for advertisement
        val settings = AdvertiseSettings.Builder()
            .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_BALANCED)
            .setConnectable(true)
            //timeout in milliseconds
            .setTimeout(60000)
            .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_MEDIUM)
            .build()

        val data = AdvertiseData.Builder()
            .setIncludeDeviceName(false)
            .setIncludeTxPowerLevel(false)
            .addServiceUuid(ParcelUuid(UUID.fromString("72839EEA-3f88-11EE-BE56-0242AC120002")))
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ActivityCompat.requestPermissions(activity, permissions, 123)
        }
        if (!checkMultiplePermissions(activity, permissions))
        {
            return
        }
        it.startAdvertising(settings, data, advertiseCallback)
    } ?: Log.w(ContentValues.TAG, "Failed to create advertiser")
}

fun checkMultiplePermissions(context: Context, permissions: Array<String>) : Boolean {
    for(permission in permissions)
    {
        if (ActivityCompat.checkSelfPermission(
                context,
                permission
            ) != PackageManager.PERMISSION_GRANTED )
        {
            return false
        }
    }
    return true
}