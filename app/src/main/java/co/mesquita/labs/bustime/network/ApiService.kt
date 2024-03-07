package co.mesquita.labs.bustime.network

import retrofit2.Retrofit
import retrofit2.http.GET


private val BASE_URL = "https://www.rmtcgoiania.com.br"
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .build()

// /index.php?option=com_rmtclinhas&view=pedhorarios&format=raw&ponto=4304
interface ApiService {
    @GET("index.php")
    suspend fun getBuss(): String
}

object Api {
    val retrofitService : ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
