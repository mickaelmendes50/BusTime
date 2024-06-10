package co.mesquita.labs.bustime.data

data class Bus(
    val number: String,
    val destination: String,
    val next: ArrivalTime,
    val following: ArrivalTime
)

