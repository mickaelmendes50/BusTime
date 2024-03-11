package co.mesquita.labs.bustime.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

class BusRepository {
    private val rmtcUrl = "https://www.rmtcgoiania.com.br/index.php?option=com_rmtclinhas&view=pedhorarios&format=raw&ponto="

    suspend fun getBusTime(
        busStop: String
    ): Result<Boolean> {
        val url = URL(rmtcUrl + busStop)
        (withContext(Dispatchers.IO) {
            url.openConnection()
        } as? HttpURLConnection)?.run {
            requestMethod = "GET"
            setRequestProperty("Content-Type", "application/json; utf-8")
            setRequestProperty("Accept", "application/json")
            setRequestProperty("Referer", "https://www.rmtcgoiania.com.br/index.php/pontos-embarque-desembarque?query=$busStop&uid=65e864f6ec8f1")
            doOutput = true
            outputStream.write(busStop.toByteArray())
            return withContext(Dispatchers.IO) {
                Result.Success(true) }
            }
        return Result.Error(Exception("Cannot open HttpURLConnection"))
    }
}