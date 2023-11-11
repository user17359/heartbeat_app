package agh.ryszard.blazej.heartbeat_app.dataClasses.supportedSensors.Movesense

fun movesenseParser(data: String, name: String): List<Float>{
    val regex = Regex("(?<=$name)[+-]?([0-9]*[.])?[0-9]+")
    return regex.findAll(data).map { it.value.format("%.2f").toFloat() }.toList()
}