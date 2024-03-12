package co.mesquita.labs.bustime.model

import android.util.Log
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

    fun getBussTime(busStop: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = busRepository.getBusTime(busStop)
            Log.d("test", result.toString())
        }
    }
}
