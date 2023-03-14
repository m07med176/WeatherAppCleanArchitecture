package iti.android.wheatherappjetpackcompose.dataLayer.source.remote

import com.google.android.gms.maps.model.LatLng
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.WeatherSuccessResponse
import retrofit2.Response

interface IRemoteDataSource {
    suspend fun getWeatherDetails(
        longitude: Double,
        latitude: Double,
        language: String,
    ): Response<WeatherSuccessResponse>


    fun getCityName(lat: Double, long: Double): String

    fun getCityName(latLng: LatLng): String

    fun checkInternetConnectivity(): Boolean

}