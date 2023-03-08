package iti.android.wheatherappjetpackcompose.domainLayer.usecase.home

import com.google.android.gms.maps.model.LatLng
import iti.android.wheatherappjetpackcompose.dataLayer.repository.IMainRepository
import iti.android.wheatherappjetpackcompose.domainLayer.models.WeatherDetailsCashMapper
import iti.android.wheatherappjetpackcompose.domainLayer.models.WeatherDetailsMapper
import iti.android.wheatherappjetpackcompose.domainLayer.models.WeatherDetailsModel
import iti.android.wheatherappjetpackcompose.utils.DataResponseState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class GetWeatherDetailsUseCase(private val repository: IMainRepository) {

    operator fun invoke(latLng: LatLng, units: String) =
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
                            repository.insertHome(WeatherDetailsCashMapper().entityFromMap(data))
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
                            emit(DataResponseState.OnSuccess(response))
                        }

                    }

            }

        }
}