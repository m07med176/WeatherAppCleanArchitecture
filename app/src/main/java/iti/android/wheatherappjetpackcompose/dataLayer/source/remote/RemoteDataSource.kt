package iti.android.wheatherappjetpackcompose.dataLayer.source.remote

import com.google.android.gms.maps.model.LatLng
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.WeatherSuccessResponse
import iti.android.wheatherappjetpackcompose.dataLayer.source.remote.geoCoder.GeoCoderAPI
import iti.android.wheatherappjetpackcompose.dataLayer.source.remote.retrofite.NetworkAPI
import retrofit2.Response


class RemoteDataSource(
    private val api: NetworkAPI,
    private val geoCoderAPI: GeoCoderAPI,
) : IRemoteDataSource {
    override suspend fun getWeatherDetails(
        longitude: Double,
        latitude: Double,
        language: String,
        units: String,
    ): Response<WeatherSuccessResponse> = api.getWeatherDetails(
        longitude = longitude,
        latitude = latitude,
        language = language,
        units = units,
    )

    override fun getCityName(lat: Double, long: Double): String = geoCoderAPI.getCityName(lat, long)

    override fun getCityName(latLng: LatLng): String = geoCoderAPI.getCityName(latLng)
    override fun checkInternetConnectivity(): Boolean = true // Fake Data Dosn't used

}