package co.mesquita.labs.bustime.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.mesquita.labs.bustime.repository.BusRepository
import co.mesquita.labs.bustime.repository.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusViewModel @Inject constructor(
    private val busRepository: BusRepository
): ViewModel() {
    private lateinit var result: Result<Boolean>
    fun getBussTime(busStop: String) {
        viewModelScope.launch(Dispatchers.IO) {
            result = busRepository.getBusTime(busStop)
            Log.d("test", result.toString())
        }
    }
}
