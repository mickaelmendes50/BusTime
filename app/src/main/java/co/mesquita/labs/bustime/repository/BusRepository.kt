package co.mesquita.labs.bustime.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

class BusRepository {
    private val rmtcUrl = "https://www.rmtcgoiania.com.br/index.php?option=com_rmtclinhas&view=pedhorarios&format=raw&ponto="

    suspend fun getBusTime(busStop: String): String {
        val url = URL(rmtcUrl + busStop)
        val connection = withContext(Dispatchers.IO) {
            url.openConnection()
        } as HttpURLConnection

        connection.requestMethod = "GET"
        connection.setRequestProperty("Referer", "https://www.rmtcgoiania.com.br/index.php/pontos-embarque-desembarque?query=$busStop&uid=65e864f6ec8f1")
        val data = connection.inputStream.bufferedReader().use { it.readText() }
        connection.disconnect()

        return data
    }
}
