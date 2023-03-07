package iti.android.wheatherappjetpackcompose.domainLayer.usecase.home

import com.google.android.gms.maps.model.LatLng
import iti.android.wheatherappjetpackcompose.domainLayer.models.WeatherDetailsMapper
import iti.android.wheatherappjetpackcompose.domainLayer.models.WeatherDetailsModel
import iti.android.wheatherappjetpackcompose.domainLayer.repository.IMainRepository
import iti.android.wheatherappjetpackcompose.utils.DataResponseState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class GetWeatherDetailsUseCase(private val repository: IMainRepository) {

    operator fun invoke(latLng: LatLng, units: String) =
        flow<DataResponseState<WeatherDetailsModel>> {
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
                        emit(DataResponseState.OnSuccess<WeatherDetailsModel>(data))
                    }

                } else {
                    emit(DataResponseState.OnError<WeatherDetailsModel>(response.await().message()))
                }
            }
        }
}