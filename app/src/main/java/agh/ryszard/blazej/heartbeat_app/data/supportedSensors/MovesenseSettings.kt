package agh.ryszard.blazej.heartbeat_app.data.supportedSensors

data class MovesenseSettings (
    override val units: List<SensorUnit> = listOf(
        SensorUnit(
            name = "ECG",
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
            parameters = listOf(
                // TODO: add parameters
            )
        ),
        SensorUnit(
            name = "Gyroscope",
            parameters = listOf(
                // TODO: add parameters
            )
        ),
        SensorUnit(
            name = "Magnetometer",
            parameters = listOf(
                // TODO: add parameters
            )
        )
    )
) : SensorSettings(units)