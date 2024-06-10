package co.mesquita.labs.bustime.data.response

data class BusTableResponse(
    val status: String,
    val mensagem: String,
    val data: List<BusInfoResponse>
)
