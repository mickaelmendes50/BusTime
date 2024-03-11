package co.mesquita.labs.bustime.model

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.mesquita.labs.bustime.repository.BusRepository
import co.mesquita.labs.bustime.repository.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BusViewModel(
    private val busRepository: BusRepository
): ViewModel() {
    private lateinit var result: Result<Boolean>
    fun getBussTime(busStop: String) {
        viewModelScope.launch(Dispatchers.IO) {
            result = busRepository.getBusTime(busStop)
            Log.d("test", "entrei no model")
        }
    }
}
