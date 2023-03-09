package iti.android.wheatherappjetpackcompose.domainLayer.usecase.home

import com.google.android.gms.maps.model.LatLng
import iti.android.wheatherappjetpackcompose.common.Constants
import iti.android.wheatherappjetpackcompose.dataLayer.repository.IMainRepository
import iti.android.wheatherappjetpackcompose.dataLayer.source.cash.Language
import iti.android.wheatherappjetpackcompose.domainLayer.models.WeatherDetailsMapper
import iti.android.wheatherappjetpackcompose.domainLayer.models.WeatherDetailsModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetWeatherDetailsUseCase(val repository: IMainRepository) {

    operator fun invoke(latLng: LatLng): Flow<HomeResponseState<WeatherDetailsModel>> {
        return repository.getSharedSettings().map { settings ->

            if (settings.userLocation?.latitude == 0.0) {
                HomeResponseState.OnNoLocationDetected<WeatherDetailsModel>()
            } else {
                if (repository.checkInternetConnectivity()) {
                    val response =
                        repository.getWeatherDetails(
                            longitude = latLng.longitude,
                            latitude = latLng.latitude,
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
//                    repository.insertHome(
//                        WeatherDetailsCashMapper().entityFromMap(
//                            responseData
//                        )
//                    )
                        // Send Data to state
                        HomeResponseState.OnSuccess<WeatherDetailsModel>(data)
                    } else {
                        HomeResponseState.OnError(response.message())
                    }
                } else {
                    HomeResponseState.OnError("")
                }
            }
        }
    }

}
//        flow<HomeResponseState<WeatherDetailsModel>> {
//
//


/*
perator fun invoke(latLng: LatLng, units: String) =
        flow<DataResponseState<WeatherDetailsModel>> {
            if (repository.checkInternetConnectivity()) {
                CoroutineScope(Dispatchers.IO).launch {
                    val response = async {
                        repository.getWeatherDetails(
                            longitude = latLng.longitude,
                            latitude = latLng.latitude,
                            units = units
                        )
                    }

                    emit(DataResponseState.OnLoading())

                    if (response.await().isSuccessful) {
                        val responseData = response.await().body()
                        if (responseData == null) {
                            emit(DataResponseState.OnNothingData<WeatherDetailsModel>())
                        } else {
                            val data = WeatherDetailsMapper().mapFromEntity(responseData)
                            data.cityName = repository.getCityName(
                                LatLng(
                                    data.lat?.toDouble() ?: 0.0,
                                    data.lon?.toDouble() ?: 0.0
                                )
                            )
                            // Insert Data in Home Room
                            repository.insertHome(
                                WeatherDetailsCashMapper().entityFromMap(
                                    responseData
                                )
                            )
                            // Send Data to state
                            emit(DataResponseState.OnSuccess<WeatherDetailsModel>(data))
                        }

                    } else {
                        // If There is no network Connectivity Send Data from Room
                        emit(
                            DataResponseState.OnError<WeatherDetailsModel>(
                                response.await().message()
                            )
                        )
                    }
                }
            } else {

                // retrieve data from Room when there is no internet Connectivity
                repository.getHome().map { WeatherDetailsCashMapper().mapFromEntity(it) }
                    .catch { emit(DataResponseState.OnError(it.message.toString())) }
                    .collectLatest { response ->
                        if (response.lat == null) {
                            emit(DataResponseState.OnNothingData())
                        } else {
                            val data = WeatherDetailsMapper().mapFromEntity(response)
                            emit(DataResponseState.OnSuccess(data))
                        }

                    }

            }

        }
 */