package agh.ryszard.blazej.heartbeat_app.data.supportedSensors

data class MovesenseSettings (
    override val units: List<SensorUnit> = listOf(
        SensorUnit(
            name = "ECG",
            paramters = listOf(
                SensorToggleParameter(
                    name = "Probing frequency",
                    options = listOf(
                        "125Hz",
                        "128Hz"
                        // TODO: add all probing frequencies
                    ),
                    uniqueId = 0
                ),
                SensorToggleParameter(
                    name = "Low-pass filter",
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
            paramters = listOf(
                // TODO: add parameters
            )
        ),
        SensorUnit(
            name = "Gyroscope",
            paramters = listOf(
                // TODO: add parameters
            )
        ),
        SensorUnit(
            name = "Magnetometer",
            paramters = listOf(
                // TODO: add parameters
            )
        )
    )
) : SensorSettings(units)