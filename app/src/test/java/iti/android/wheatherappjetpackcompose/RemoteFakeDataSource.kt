package iti.android.wheatherappjetpackcompose

import com.google.android.gms.maps.model.LatLng
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.WeatherSuccessResponse
import iti.android.wheatherappjetpackcompose.dataLayer.source.remote.IRemoteDataSource
import retrofit2.Response

class RemoteFakeDataSource(
    var weatherSuccessResponse: WeatherSuccessResponse,
) : IRemoteDataSource {
    override suspend fun getWeatherDetails(
        longitude: Double,
        latitude: Double,
        language: String,
        units: String,
    ): Response<WeatherSuccessResponse> {
        return Response.success(weatherSuccessResponse)
    }

    override fun getCityName(lat: Double, long: Double): String {
        return "Mansoura"
    }

    override fun getCityName(latLng: LatLng): String {
        return "Mansoura"
    }

    override fun checkInternetConnectivity(): Boolean {
        return true
    }
}