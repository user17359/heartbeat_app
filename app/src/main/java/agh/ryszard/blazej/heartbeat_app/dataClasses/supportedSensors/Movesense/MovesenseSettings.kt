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
                    name = "Probing frequency [Hz]",
                    encodedName = "probing",
                    options = listOf(
                        "125",
                        "128",
                        "200",
                        "250",
                        "256",
                        "500",
                        "512"
                    ),
                    uniqueId = 0
                ),
            ),
            dataParser = {data: String -> movesenseParser(data, "ecg") },
            dataChannels = 1,
            dataSamples = 16
        ),
        SensorUnit(
            name = "Accelerometer",
            encodedName = "acc",
            parameters = listOf(
                SensorToggleParameter(
                    name = "Probing frequency [Hz]",
                    encodedName = "probing",
                    options = listOf(
                        "13",
                        "26",
                        "52",
                        "104",
                        "208",
                        "416",
                        "833",
                        "1666"
                    ),
                    uniqueId = 1
                ),
            ),
            dataParser = {data: String -> movesenseParser(data, "acc") },
            dataChannels = 3,
            dataSamples = 1
        ),
        SensorUnit(
            name = "Gyroscope",
            encodedName = "gyro",
            parameters = listOf(
                SensorToggleParameter(
                    name = "Probing frequency [Hz]",
                    encodedName = "probing",
                    options = listOf(
                        "13",
                        "26",
                        "52",
                        "104",
                        "208",
                        "416",
                        "833",
                        "1666"
                    ),
                    uniqueId = 2
                ),
            ),
            dataParser = {data: String -> movesenseParser(data, "gyro") },
            dataChannels = 3,
            dataSamples = 1
        ),
        SensorUnit(
            name = "Magnetometer",
            encodedName = "mag",
            parameters = listOf(
                SensorToggleParameter(
                    name = "Probing frequency [Hz]",
                    encodedName = "probing",
                    options = listOf(
                        "13",
                        "26",
                        "52",
                        "104",
                        "208",
                        "416",
                        "833",
                        "1666"
                    ),
                    uniqueId = 3
                ),
            ),
            dataParser = {data: String -> movesenseParser(data, "mag") },
            dataChannels = 3,
            dataSamples = 1
        ),
        SensorUnit(
            name = "Heart rate",
            encodedName = "hr",
            parameters = listOf(
            ),
            dataParser = {data: String -> movesenseParser(data, "hr") },
            dataChannels = 1,
            dataSamples = 1
        )
    ),
    override val tag: TypeTag
) : SensorSettings(units, tag, ::movesenseValidator)