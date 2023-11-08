package agh.ryszard.blazej.heartbeat_app.data.supportedSensors

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
        ),
        SensorUnit(
            name = "Accelerometer",
            encodedName = "acc",
            parameters = listOf(
                // TODO: add parameters
            )
        ),
        SensorUnit(
            name = "Gyroscope",
            encodedName = "gyro",
            parameters = listOf(
                // TODO: add parameters
            )
        ),
        SensorUnit(
            name = "Magnetometer",
            encodedName = "mag",
            parameters = listOf(
                // TODO: add parameters
            )
        )
    )
) : SensorSettings(units)