package iti.android.wheatherappjetpackcompose.presentationLayer.ui.home

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import iti.android.wheatherappjetpackcompose.domainLayer.models.WeatherDetailsModel
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.home.HomeResponseState
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.home.HomeUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val useCases: HomeUseCases) : ViewModel() {

    private val _state =
        MutableStateFlow<HomeResponseState<WeatherDetailsModel>>(HomeResponseState.OnLoading())
    val state: StateFlow<HomeResponseState<WeatherDetailsModel>>
        get() = _state

    fun getWeatherData(latLng: LatLng? = null) {
        viewModelScope.launch {
            useCases.getWeatherDetailsUseCase.invoke(latLng)
                .catch {
                    _state.value =
                        HomeResponseState.OnError<WeatherDetailsModel>(it.message.toString())
                }
                .collect {
                    _state.value = it
                }
        }
    }

    fun saveLocation(location: Location?) {
        getWeatherData(LatLng(location?.latitude ?: 0.0, location?.longitude ?: 0.0))
        useCases.updateGPSLocation.invoke(
            LatLng(
                location?.latitude ?: 0.0,
                location?.longitude ?: 0.0
            )
        )

    }


}