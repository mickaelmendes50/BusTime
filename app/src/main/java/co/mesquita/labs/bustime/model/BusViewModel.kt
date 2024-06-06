package co.mesquita.labs.bustime.model

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.mesquita.labs.bustime.api.Endpoints
import co.mesquita.labs.bustime.util.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class BusViewModel : ViewModel() {
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun isValidStopId(stopId: String): LiveData<Boolean> {
        val resultLiveData = MutableLiveData<Boolean>()
        val retrofitClient = NetworkUtils.getRetrofitInstance()
        val service = retrofitClient.create(Endpoints::class.java)
        _isLoading.postValue(true)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = service.isStopIdValid(stopId)
                if (response.isSuccessful) {
                    val status = response.body()?.get("status")?.asString
                    resultLiveData.postValue(status == "sucesso")
                }
            } catch (e: HttpException) {
                Log.e("Retrofit", "Exception ${e.message}")
            }
            _isLoading.postValue(false)
        }
        return resultLiveData
    }

    fun getBussTime(stopId: String): LiveData<String> {
        val resultLiveData = MutableLiveData<String>()
        val retrofitClient = NetworkUtils.getRetrofitInstance("string")
        val service = retrofitClient.create(Endpoints::class.java)
        _isLoading.postValue(true)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = service.getBusTime(stopId)
                if (response.isSuccessful)
                    resultLiveData.postValue(response.body())
            } catch (e: HttpException) {
                Log.e("Retrofit", "Exception ${e.message}")
            }
            _isLoading.postValue(false)
        }
        return resultLiveData
    }
}
