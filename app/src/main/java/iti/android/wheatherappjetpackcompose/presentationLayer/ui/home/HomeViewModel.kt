package iti.android.wheatherappjetpackcompose.presentationLayer.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import iti.android.wheatherappjetpackcompose.domainLayer.models.WeatherDetailsModel
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.home.HomeUseCases
import iti.android.wheatherappjetpackcompose.utils.DataResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val useCases: HomeUseCases) : ViewModel() {

    private val _state =
        MutableStateFlow<DataResponseState<WeatherDetailsModel>>(DataResponseState.OnLoading())
    val state: StateFlow<DataResponseState<WeatherDetailsModel>>
        get() = _state

    fun getWeatherData(latLng: LatLng, units: String) {
        viewModelScope.launch {
            useCases.getWeatherDetailsUseCase.invoke(latLng, units).collect {
                _state.value = it
            }
        }
    }


}