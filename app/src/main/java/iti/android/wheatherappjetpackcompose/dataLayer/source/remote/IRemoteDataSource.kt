package iti.android.wheatherappjetpackcompose.dataLayer.source.remote

import com.google.android.gms.maps.model.LatLng
import iti.android.wheatherappjetpackcompose.common.Constants
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.WeatherSuccessResponse
import iti.android.wheatherappjetpackcompose.dataLayer.source.remote.retrofite.Units
import retrofit2.Response

interface IRemoteDataSource {
    suspend fun getWeatherDetails(
        apiKey: String = Constants.API_KEY,
        exclude: String? = null,
        longitude: Double,
        latitude: Double,
        units: String = Units.metric.name,
        language: String,

        ): Response<WeatherSuccessResponse>


    fun getCityName(lat: Double, long: Double): String

    fun getCityName(latLng: LatLng): String
}