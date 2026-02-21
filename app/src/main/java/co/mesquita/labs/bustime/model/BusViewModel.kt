package co.mesquita.labs.bustime.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.mesquita.labs.bustime.api.Endpoints
import co.mesquita.labs.bustime.data.ArrivalTime
import co.mesquita.labs.bustime.data.Bus
import co.mesquita.labs.bustime.data.response.BusTableResponse
import co.mesquita.labs.bustime.util.NetworkUtils
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class BusViewModel : ViewModel() {
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isValidStopId = MutableLiveData<Boolean?>(null)
    val isValidStopId: LiveData<Boolean?> = _isValidStopId

    private val _busList = MutableLiveData<List<Bus>>()
    val busList: LiveData<List<Bus>> = _busList

    fun resetValidation() {
        _isValidStopId.value = null
        _isLoading.value = false
    }

    fun validateStopId(stopId: Int) {
        val retrofitClient = NetworkUtils.getWebInstance("json")
        val service = retrofitClient.create(Endpoints::class.java)
        _isLoading.postValue(true)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = service.isStopIdValid(stopId.toString())
                if (response.isSuccessful) {
                    val status = response.body()?.get("status")?.asString
                    _isValidStopId.postValue(status == "sucesso")
                }
            } catch (e: HttpException) {
                Log.e("Retrofit", "Exception ${e.message}")
            }
            _isLoading.postValue(false)
        }
    }

    fun updateBusTable(stopId: Int) {
        val retrofitClient = NetworkUtils.getAppInstance()
        val service = retrofitClient.create(Endpoints::class.java)
        _isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = service.postBusTable(stopId.toString())
                val json = response.body()
                if (json != null) {
                    val busStopResponse = parseResponse(json)
                    updateBusInfo(busStopResponse)
                }
            } catch (e: Exception) {
                Log.e("Retrofit", "Exception ${e.message}")
            }
            _isLoading.postValue(false)
        }
    }

    private fun parseResponse(json: JsonObject): BusTableResponse {
        val gson = Gson()
        val type = object : TypeToken<BusTableResponse>() {}.type
        return gson.fromJson(json, type)
    }

    private fun updateBusInfo(res: BusTableResponse) {
        val busList = ArrayList<Bus>()
        res.data.forEach {
            val nextTime = if (it.Proximo.PrevisaoChegada == 0) "<1"
                           else it.Proximo.PrevisaoChegada.toString().padStart(2, '0')
            val followingTime = if (it.Seguinte?.PrevisaoChegada == 0) "<1"
                                else it.Seguinte?.PrevisaoChegada?.toString()?.padStart(2, '0') ?: "--"
            val bus = Bus(
                number = it.Linha,
                destination = it.Destino,
                next = ArrivalTime(
                    time = nextTime,
                    isReal = it.Proximo.Qualidade == "Tempo Real"
                ),
                following = if (it.Seguinte != null) {
                    ArrivalTime(
                        time = followingTime,
                        isReal = it.Seguinte.Qualidade == "Tempo Real"
                    )
                } else {
                    ArrivalTime(
                        time = "--",
                        isReal = true
                    )
                }
            )
            busList.add(bus)
        }
        _busList.postValue(busList)
    }
}
