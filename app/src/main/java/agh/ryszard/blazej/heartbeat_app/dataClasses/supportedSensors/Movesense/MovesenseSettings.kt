package agh.ryszard.blazej.heartbeat_app.dataClasses.supportedSensors.Movesense

import agh.ryszard.blazej.heartbeat_app.dataClasses.supportedSensors.SensorSettings
import agh.ryszard.blazej.heartbeat_app.dataClasses.supportedSensors.TypeTag
import agh.ryszard.blazej.heartbeat_app.dataClasses.supportedSensors.SensorToggleParameter
import agh.ryszard.blazej.heartbeat_app.dataClasses.supportedSensors.SensorUnit

data class MovesenseSettings (
    override val units: List<SensorUnit> = listOf(
        SensorUnit(
            name = "ECG",
            encodedName = "ecg",
            parameters = listOf(
                SensorToggleParameter(
                    name = "Probing frequency",
                    encodedName = "probing",
                    options = listOf(
                        "125Hz",
                        "128Hz"
                        // TODO: add all probing frequencies
                    ),
                    uniqueId = 0
                ),
                SensorToggleParameter(
                    name = "Low-pass filter",
                    encodedName = "lowPass",
                    options = listOf(
                        "40Hz",
                        "100Hz",
                        "150Hz"
                    ),
                    uniqueId = 1
                )
            ),
            dataParser = {data: String -> movesenseParser(data, "ecg") },
            dataChannels = 1,
            dataSamples = 16
        ),
        SensorUnit(
            name = "Accelerometer",
            encodedName = "acc",
            parameters = listOf(
                // TODO: add parameters
            ),
            dataParser = {data: String -> movesenseParser(data, "acc") },
            dataChannels = 3,
            dataSamples = 1
        ),
        SensorUnit(
            name = "Gyroscope",
            encodedName = "gyro",
            parameters = listOf(
                // TODO: add parameters
            ),
            dataParser = {data: String -> movesenseParser(data, "gyro") },
            dataChannels = 3,
            dataSamples = 1
        ),
        SensorUnit(
            name = "Magnetometer",
            encodedName = "mag",
            parameters = listOf(
                // TODO: add parameters
            ),
            dataParser = {data: String -> movesenseParser(data, "mag") },
            dataChannels = 3,
            dataSamples = 1
        )
    ),
    override val tag: TypeTag
) : SensorSettings(units, tag, ::movesenseValidator)