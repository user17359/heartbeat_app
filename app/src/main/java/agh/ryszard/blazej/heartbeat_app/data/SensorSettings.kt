package agh.ryszard.blazej.heartbeat_app.data

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
    open val options: List<String>,
    open val uniqueId: Int
)

data class SensorToggleParameter (
    override val options: List<String>,
    override val name: String,
    override val uniqueId: Int
): SensorParameter(name, ParameterType.Toggle, options, uniqueId)