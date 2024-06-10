package co.mesquita.labs.bustime.api

import co.mesquita.labs.bustime.util.Constants.REQUEST_HEADER
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface Endpoints {
    @FormUrlEncoded
    @POST("/horariodeviagem/validar")
    @Headers(REQUEST_HEADER)
    suspend fun isStopIdValid(
        @Field("txtNumeroPonto") stopId: String
    ): Response<JsonObject>

    @FormUrlEncoded
    @POST("/pontoparada/previsaochegada")
    suspend fun postBusTable(
        @Field("qryIdPontoParada") stopId: String
    ): Response<JsonObject>

    @FormUrlEncoded
    @POST("/pontoparada/previsaochegada")
    suspend fun postBusLocation(
        @Field("qryIdPontoParada") stopId: String,
        @Field("qryIdVeiculo") busId: String
    ): Response<JsonObject>
}
