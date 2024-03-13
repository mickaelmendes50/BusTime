package co.mesquita.labs.bustime.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.mesquita.labs.bustime.repository.BusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusViewModel @Inject constructor(
    private val busRepository: BusRepository
): ViewModel() {

    fun getBussTime(busStop: String): LiveData<String> {
        val resultLiveData = MutableLiveData<String>()
        viewModelScope.launch(Dispatchers.IO) {
            val result = busRepository.getBusTime(busStop)
            resultLiveData.postValue(result)
        }
        return resultLiveData
    }
}
