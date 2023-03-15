package iti.android.wheatherappjetpackcompose.domainLayer.usecase.home

import com.google.android.gms.maps.model.LatLng
import iti.android.wheatherappjetpackcompose.common.Constants
import iti.android.wheatherappjetpackcompose.dataLayer.repository.RepositoryInterface
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.cash.Language
import iti.android.wheatherappjetpackcompose.domainLayer.models.WeatherDetailsCashMapper
import iti.android.wheatherappjetpackcompose.domainLayer.models.WeatherDetailsMapper
import iti.android.wheatherappjetpackcompose.domainLayer.models.WeatherDetailsModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class GetWeatherDetailsUseCase(val repository: RepositoryInterface) {

    operator fun invoke(latLng: LatLng? = null): Flow<HomeResponseState<WeatherDetailsModel>> {
        return repository.getSharedSettings().map { settings ->

            if (settings.userLocation?.latitude == 0.0) {
                HomeResponseState.OnNoLocationDetected<WeatherDetailsModel>()
            } else {
                if (repository.checkInternetConnectivity()) {
                    val response =
                        repository.getWeatherDetails(
                            // check if latLng is Empty so set Cashed Location
                            longitude = latLng?.longitude ?: (settings.userLocation?.longitude
                                ?: 0.0),
                            latitude = latLng?.latitude ?: (settings.userLocation?.latitude ?: 0.0),
                            language = if (settings.language == Language.Arabic) Constants.ARABIC else Constants.ENGLISH
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

                        // Insert Data in Home Room
                        repository.insertHome(WeatherDetailsCashMapper().entityFromMap(data))

                        // Send Data to state
                        HomeResponseState.OnSuccess<WeatherDetailsModel>(data)
                    } else {
                        HomeResponseState.OnError(response.message())
                    }
                } else {
                    repository.getHome().catch {
                        HomeResponseState.OnError<WeatherDetailsModel>(it.message.toString())
                    }.map { homeEntity ->
                        if (homeEntity != null)
                            HomeResponseState.OnSuccess<WeatherDetailsModel>(
                                WeatherDetailsCashMapper().mapFromEntity(homeEntity)
                            )
                        else
                            HomeResponseState.OnError<WeatherDetailsModel>("No Cashed Data")
                    }.first()
                }
            }
        }
    }
}