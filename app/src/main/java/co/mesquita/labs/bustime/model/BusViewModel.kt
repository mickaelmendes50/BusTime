package co.mesquita.labs.bustime.model

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.mesquita.labs.bustime.api.Endpoints
import co.mesquita.labs.bustime.components.BusChip
import co.mesquita.labs.bustime.util.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import retrofit2.HttpException

class BusViewModel : ViewModel() {
    private val _isLoading = MutableLiveData(false)
    private val _destinyList = MutableLiveData<List<String>>()
    private val _busNumberList = MutableLiveData<List<String>>()
    private val _nextTimeList = MutableLiveData<List<String>>()
    private val _anotherNextList = MutableLiveData<List<String>>()

    val isLoading: LiveData<Boolean> = _isLoading
    val destinyList: LiveData<List<String>> get() = _destinyList
    val busNumberList: LiveData<List<String>> get() = _busNumberList
    val nextTimeList: LiveData<List<String>> get() = _nextTimeList
    val anotherNextList: LiveData<List<String>> get() = _anotherNextList

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
        val destinies = mutableListOf<String>()
        val busNumbers = mutableListOf<String>()
        val nextTimes = mutableListOf<String>()
        val anotherNexts = mutableListOf<String>()
        val retrofitClient = NetworkUtils.getRetrofitInstance("string")
        val service = retrofitClient.create(Endpoints::class.java)
        _isLoading.postValue(true)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = service.getBusTime(stopId)
                if (response.isSuccessful) {
                    resultLiveData.postValue(response.body())
                    val document: Document = Jsoup.parse(response.body())
                    val timeTable = document.select("table.table-sm.table-striped.subtab-previsoes")
                    for (line in timeTable.select("tr").drop(1)) {
                        val columns = line.select("td")
                        val busNumber = columns[0].text()
                        val destiny = columns[1].text()
                        val nextTimeElement = columns[2]
                        val anotherNextElement = columns[3]

                        val nextTime = if (nextTimeElement.text() != "--") {
                            nextTimeElement.ownText().replace(Regex("[^\\d]+"), "")
                                .replace("(Aprox.)", "?")
                        } else {
                            "--"
                        }

                        val anotherNext = if (anotherNextElement.text() != "--") {
                            anotherNextElement.ownText().replace(Regex("[^\\d]+"), "")
                                .replace("(Aprox.)", "?")
                        } else {
                            "--"
                        }

                        destinies.add(destiny)
                        busNumbers.add(busNumber)
                        nextTimes.add(nextTime)
                        anotherNexts.add(anotherNext)
                    }
                }
            } catch (e: HttpException) {
                Log.e("Retrofit", "Exception ${e.message}")
            }
            _isLoading.postValue(false)
        }
        updateBusInfo(destinies, busNumbers, nextTimes, anotherNexts)
        return resultLiveData
    }

    private fun updateBusInfo(destiny: List<String>, busNumber: List<String>, nextTime: List<String>, anotherNext: List<String>) {
        _destinyList.value = destiny
        _busNumberList.value = busNumber
        _nextTimeList.value = nextTime
        _anotherNextList.value = anotherNext
    }
}
