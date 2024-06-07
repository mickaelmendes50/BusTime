package co.mesquita.labs.bustime.util

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class NetworkUtils {
    companion object {
        fun getRetrofitInstance(url: String, resultType: String) : Retrofit {
            if (resultType == "json") return Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            if (resultType == "string") return Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
            return Retrofit.Builder()
                .baseUrl(url)
                .build()
        }

        fun getRetrofitInstance(url: String) : Retrofit {
            return Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}
