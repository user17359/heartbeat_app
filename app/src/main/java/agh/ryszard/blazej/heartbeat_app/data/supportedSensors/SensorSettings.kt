package agh.ryszard.blazej.heartbeat_app.data.supportedSensors

enum class ParameterType {
    Toggle
}
open class SensorSettings (
    open val units: List<SensorUnit>
)

class SensorUnit (
    val name: String,
    val encodedName: String,
    val parameters: List<SensorParameter>,
    val dataParser: (String) -> List<Float>,
    val dataChannels: Int,
    val dataSamples: Int
)
open class SensorParameter (
    open val name: String,
    open val encodedName: String,
    val type: ParameterType,
    open val uniqueId: Int
)

data class SensorToggleParameter (
    val options: List<String>,
    override val name: String,
    override val uniqueId: Int,
    override val encodedName: String
): SensorParameter(name, encodedName, ParameterType.Toggle, uniqueId)