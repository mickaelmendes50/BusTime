package co.mesquita.labs.bustime.model

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import co.mesquita.labs.bustime.api.Endpoints
import co.mesquita.labs.bustime.repository.BusRepository
import co.mesquita.labs.bustime.util.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class BusViewModel @Inject constructor(
    private val busRepository: BusRepository
): ViewModel() {
    val isLoading = mutableStateOf(false)

    fun isStopIdValid(stopId: String): LiveData<Boolean> {
        val retrofitClient = NetworkUtils.getRetrofitInstance()
        val service = retrofitClient.create(Endpoints::class.java)
        val resultLiveData = MutableLiveData<Boolean>()
        isLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            val response = service.isStopIdValid(stopId)
            try {
                if (response.isSuccessful) {
                    val status = response.body()?.get("status")?.asString
                    resultLiveData.postValue(status == "sucesso")
                }
            } catch (e: HttpException) {
                Log.e("Retrofit", "Exception ${e.message}")
            }
            isLoading.value = false
        }
        return resultLiveData
    }

    fun getBussTime(busStop: String): LiveData<String> {
        isLoading.value = true
        val resultLiveData = MutableLiveData<String>()
        viewModelScope.launch(Dispatchers.IO) {
            val result = busRepository.getBusTime(busStop)
            resultLiveData.postValue(result)
            isLoading.value = false
        }
        return resultLiveData
    }
}
