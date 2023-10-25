package agh.ryszard.blazej.heartbeat_app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.juul.kable.AndroidAdvertisement
import com.juul.kable.Filter
import com.juul.kable.Scanner
import com.juul.kable.logs.Logging
import com.juul.kable.logs.SystemLogEngine

// Time after we stop scanning
private const val SCAN_PERIOD: Long = 60000

class ScanViewModel: ViewModel() {

    val listOfDevices = MutableLiveData<Set<AndroidAdvertisement>>()
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
                add(result)
            }?.toSet())
        }
    }
}
