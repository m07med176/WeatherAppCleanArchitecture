package iti.android.wheatherappjetpackcompose.domainLayer.usecase.home

import com.google.android.gms.maps.model.LatLng
import iti.android.wheatherappjetpackcompose.common.Constants
import iti.android.wheatherappjetpackcompose.dataLayer.repository.RepositoryInterface
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.cash.Language
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.cash.Temperature
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.cash.getSharedSettings
import iti.android.wheatherappjetpackcompose.dataLayer.source.remote.retrofite.Units
import iti.android.wheatherappjetpackcompose.domainLayer.models.WeatherDetailsCashMapper
import iti.android.wheatherappjetpackcompose.domainLayer.models.WeatherDetailsMapper
import iti.android.wheatherappjetpackcompose.domainLayer.models.WeatherDetailsModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class GetWeatherDetailsUseCase(val repository: RepositoryInterface) {

    operator fun invoke(latLng: LatLng? = null): Flow<HomeResponseState<WeatherDetailsModel>> =
        flow {
            val settings = repository.context.getSharedSettings()

            var unit = Units.metric.name
            when (settings.temperature) {
                Temperature.Celsius -> {
                    unit = Units.metric.name
                }
                Temperature.Kelvin -> {
                    unit = Units.standard.name
                }
                Temperature.Fahrenheit -> {
                    unit = Units.imperial.name
                }
            }
            if (settings.userLocation?.latitude == 0.0) {
                emit(HomeResponseState.OnNoLocationDetected<WeatherDetailsModel>())
            } else {
                if (repository.checkInternetConnectivity()) {
                    val response =
                        repository.getWeatherDetails(
                            // check if latLng is Empty so set Cashed Location
                            longitude = latLng?.longitude ?: (settings.userLocation?.longitude
                                ?: 0.0),
                            latitude = latLng?.latitude ?: (settings.userLocation?.latitude ?: 0.0),
                            language = if (settings.language == Language.Arabic) Constants.ARABIC else Constants.ENGLISH,
                            units = unit
                        )

                    if (response.isSuccessful) {
                        val responseData = response.body()
                        val data = WeatherDetailsMapper().mapFromEntity(responseData!!)
                        data.cityName = repository.getCityName(
                            LatLng(
                                data.lat?.toDouble() ?: 0.0,
                                data.lon?.toDouble() ?: 0.0
                            )
                        )

                        when (settings.temperature) {
                            Temperature.Celsius -> {
                                data.currentModel?.temp = (data.currentModel?.temp ?: "0") + " °C"
                            }
                            Temperature.Kelvin -> {
                                data.currentModel?.temp = (data.currentModel?.temp ?: "0") + " °K"
                            }
                            Temperature.Fahrenheit -> {
                                data.currentModel?.temp = (data.currentModel?.temp ?: "0") + " °F"
                            }
                        }


                        // Insert Data in Home Room
                        repository.insertHome(WeatherDetailsCashMapper().entityFromMap(data))

                        // Send Data to state
                        emit(HomeResponseState.OnSuccess<WeatherDetailsModel>(data))
                    } else {
                        emit(HomeResponseState.OnError(response.message()))
                    }
                } else {
                    repository.getHome().catch {
                        emit(HomeResponseState.OnError<WeatherDetailsModel>(it.message.toString()))
                    }.collect { homeEntity ->
                        if (homeEntity != null)
                            emit(
                                HomeResponseState.OnSuccess<WeatherDetailsModel>(
                                    WeatherDetailsCashMapper().mapFromEntity(homeEntity)
                                )
                            )
                        else
                            emit(HomeResponseState.OnError<WeatherDetailsModel>("No Cashed Data"))
                    }
                }
        }
    }
}