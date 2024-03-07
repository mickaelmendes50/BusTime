package co.mesquita.labs.bustime.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.mesquita.labs.bustime.network.Api
import kotlinx.coroutines.launch

class BussModel : ViewModel() {
    private var busState: String by mutableStateOf("")

    private fun getBuss() {
        viewModelScope.launch {
            val listResult = Api.retrofitService.getBuss()
            busState = listResult
        }
    }
}
