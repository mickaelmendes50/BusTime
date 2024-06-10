package co.mesquita.labs.bustime.data.response

data class BusTimingResponse(
    val Qualidade: String,
    val NumeroOnibus: String?,
    val HoraChegadaPlanejada: String,
    val HoraChegadaPrevista: String,
    val PrevisaoChegada: Int
)
