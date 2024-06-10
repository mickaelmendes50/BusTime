package co.mesquita.labs.bustime.data.response

data class BusInfoResponse(
    val Linha: String,
    val Destino: String,
    val Proximo: BusTimingResponse,
    val Seguinte: BusTimingResponse?
)
