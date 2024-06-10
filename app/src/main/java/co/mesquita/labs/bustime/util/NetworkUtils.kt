package co.mesquita.labs.bustime.util

import co.mesquita.labs.bustime.util.Constants.BASE_APP_URL
import co.mesquita.labs.bustime.util.Constants.BASE_WEB_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class NetworkUtils {
    companion object {
        fun getWebInstance(resultType: String) : Retrofit {
            if (resultType == "json") return Retrofit.Builder()
                .baseUrl(BASE_WEB_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            if (resultType == "string") return Retrofit.Builder()
                .baseUrl(BASE_WEB_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_WEB_URL)
                .build()
        }

        fun getAppInstance() : Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_APP_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}
