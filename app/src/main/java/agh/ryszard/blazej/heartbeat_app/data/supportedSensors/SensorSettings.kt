package agh.ryszard.blazej.heartbeat_app.data.supportedSensors

enum class ParameterType {
    Toggle
}
open class SensorSettings (
    open val units: List<SensorUnit>
)

class SensorUnit (
    val name: String,
    val paramters: List<SensorParameter>
)
open class SensorParameter (
    open val name: String,
    val type: ParameterType,
    open val uniqueId: Int
)

data class SensorToggleParameter (
    val options: List<String>,
    override val name: String,
    override val uniqueId: Int
): SensorParameter(name, ParameterType.Toggle, uniqueId)