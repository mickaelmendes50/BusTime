package co.mesquita.labs.bustime.api

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface Endpoints {
    @FormUrlEncoded
    @POST("/horariodeviagem/validar")
    @Headers("Referer: https://m.rmtcgoiania.com.br/horariodeviagem")
    fun isStopIdValid(
        @Field("txtNumeroPonto") stopId: String
    ): Call<JsonObject>
}
