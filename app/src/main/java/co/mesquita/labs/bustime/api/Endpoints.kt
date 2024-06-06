package co.mesquita.labs.bustime.api

import co.mesquita.labs.bustime.util.Constants.REQUEST_HEADER
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface Endpoints {
    @FormUrlEncoded
    @POST("/horariodeviagem/validar")
    @Headers(REQUEST_HEADER)
    suspend fun isStopIdValid(
        @Field("txtNumeroPonto") stopId: String
    ): Response<JsonObject>

    @GET("/horariodeviagem/visualizar/ponto/{stopId}")
    @Headers(REQUEST_HEADER)
    suspend fun getBusTime(
        @Path(value = "stopId") stopId: String
    ): Response<String>
}
