package agh.ryszard.blazej.heartbeat_app.viewmodel

import agh.ryszard.blazej.heartbeat_app.data.BtDevice
import agh.ryszard.blazej.heartbeat_app.data.Measurement
import agh.ryszard.blazej.heartbeat_app.utils.peripheralScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.juul.kable.AndroidAdvertisement
import com.juul.kable.Filter
import com.juul.kable.Peripheral
import com.juul.kable.Scanner
import com.juul.kable.State
import com.juul.kable.characteristicOf
import com.juul.kable.logs.Logging
import com.juul.kable.logs.SystemLogEngine
import com.juul.kable.peripheral
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

// Time after we stop scanning
private const val SCAN_PERIOD: Long = 60000

class ScanViewModel: ViewModel() {

    val listOfDevices = MutableLiveData<Set<AndroidAdvertisement>>()
    val connectionState = MutableLiveData<State>()
    val reconnectState = MutableLiveData(false)

    private val _foundDevices = mutableListOf<String>()
    private val _rememberedSensors = mutableSetOf<String>()
    private var _peripheral: Peripheral? = null

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        throwable.printStackTrace()
    }

    private val _scope = CoroutineScope(peripheralScope.coroutineContext + Job(peripheralScope.coroutineContext.job) + coroutineExceptionHandler)

    init {
        listOfDevices.value = setOf()
    }

    suspend fun scanLeDevice() {
        Scanner {
            filters = listOf(
                Filter.NamePrefix("Heartbeat")
            )
            logging {
                engine = SystemLogEngine
                level = Logging.Level.Warnings
                format = Logging.Format.Multiline
            }
        }.advertisements.collect{ result ->
            listOfDevices.postValue(listOfDevices.value?.toMutableList()?.apply {
                if(!_foundDevices.contains(result.address)) {
                    add(result)
                    _foundDevices.add(result.address)
                }
            }?.toSet())
        }
    }

    fun connectLePeripheral(advertisement: AndroidAdvertisement) {
        listOfDevices.value = setOf()
        _foundDevices.clear()
        val peripheral = _scope.peripheral(advertisement) {

        }
        _peripheral = peripheral
        _scope.launch {
            asyncConnection()
        }
    }

    fun disconnectLePeripheral() {
        val disconnectScope = CoroutineScope(peripheralScope.coroutineContext + Job(peripheralScope.coroutineContext.job) + coroutineExceptionHandler)
        disconnectScope.launch {
            asyncDisconnection()
        }
    }

    private suspend fun asyncConnection() {
        _peripheral!!.connect()
        _peripheral!!.state.collect { state ->
            connectionState.postValue(state)
        }
    }

    private suspend fun asyncDisconnection() {
        _peripheral!!.disconnect()
        _peripheral!!.state.collect { state ->
            connectionState.postValue(state)
        }
    }

    //TODO: separate into different viewModels
    //TODO: clean up UUIDs
    suspend fun getSensors(): List<BtDevice> {
        val characteristic = characteristicOf(
            service = "6672b3e6-477e-4e52-a3fb-a440c57dc857",
            characteristic = "9f03f5db-93ba-402b-951f-1c8e008b5adc",
        )
        val reading = _peripheral!!.read(characteristic).decodeToString()
        val sensors: List<BtDevice> = Json.decodeFromString(reading)
        sensors.forEach{ sensor ->
            _rememberedSensors.add(sensor.mac)
        }
        return sensors
    }

    suspend fun findSensors(): List<BtDevice> {
        val characteristic = characteristicOf(
            service = "6672b3e6-477e-4e52-a3fb-a440c57dc857",
            characteristic = "5fc4077d-e88f-4b5d-956b-955d30ec5899",
        )
        val reading = _peripheral!!.read(characteristic).decodeToString()
        val sensors: List<BtDevice> = Json.decodeFromString(reading)
        val filteredSensors = mutableListOf<BtDevice>()
        sensors.forEach{ sensor ->
            if(sensor.mac !in _rememberedSensors){
                filteredSensors.add(sensor)
            }
        }
        return filteredSensors
    }

    suspend fun addSensor(sensor: BtDevice) {
        val characteristic = characteristicOf(
            service = "6672b3e6-477e-4e52-a3fb-a440c57dc857",
            characteristic = "1fe83b02-0788-4af7-9a69-af6b9e9782a7",
        )
        val jsonString = Json.encodeToString(sensor)
        _peripheral!!.write(characteristic, jsonString.toByteArray(Charsets.UTF_8))
    }

    suspend fun addMeasurement(measurement: Measurement) {
        val characteristic = characteristicOf(
            service = "a56f5e06-fd24-4ffe-906f-f82e916262bc",
            characteristic = "18c7e933-73cf-4d47-9973-51a53f0fec4e",
        )
        reconnectState.postValue(false)
        val jsonString = Json.encodeToString(measurement)
        _peripheral!!.write(characteristic, jsonString.toByteArray(Charsets.UTF_8))
    }
    
    // for multi-connection workaround
    suspend fun timedReconnect(timeMilis: Long) {
        _peripheral!!.disconnect()
        delay(timeMilis)
        reconnectState.postValue(true)
        print(_peripheral!!.state)
        _peripheral!!.connect()
        _peripheral!!.state.collect { state ->
            connectionState.postValue(state)
        }
    }
            
}
