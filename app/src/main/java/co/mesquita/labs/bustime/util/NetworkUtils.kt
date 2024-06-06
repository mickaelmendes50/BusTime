package co.mesquita.labs.bustime.util

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkUtils {
    companion object {
        private const val BASE_URL = "https://m.rmtcgoiania.com.br/"

        fun getRetrofitInstance() : Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}
