package iti.android.wheatherappjetpackcompose.domainLayer.usecase.home

import com.google.android.gms.maps.model.LatLng
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.WeatherSuccessResponse
import iti.android.wheatherappjetpackcompose.domainLayer.models.WeatherDetailsMapper
import iti.android.wheatherappjetpackcompose.domainLayer.models.WeatherDetailsModel
import iti.android.wheatherappjetpackcompose.domainLayer.repository.IMainRepository
import iti.android.wheatherappjetpackcompose.utils.ApiResponseState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class GetWeatherDetailsUseCase(private val repository: IMainRepository) {

    operator fun invoke(latLng: LatLng, units: String) =
        flow<ApiResponseState<WeatherDetailsModel>> {
            CoroutineScope(Dispatchers.IO).launch {
                val response = async {
                    repository.getWeatherDetails(
                        longitude = latLng.longitude,
                        latitude = latLng.latitude,
                        units = units
                    )
                }

                emit(ApiResponseState.OnLoading())
                if (response.await().isSuccessful) {
                    val data = WeatherDetailsMapper().mapFromEntity(
                        response.await().body() ?: WeatherSuccessResponse()
                    )
                    emit(ApiResponseState.OnSuccess<WeatherDetailsModel>(data))
                } else {
                    emit(ApiResponseState.OnError<WeatherDetailsModel>(response.await().message()))
                }
            }
        }
}