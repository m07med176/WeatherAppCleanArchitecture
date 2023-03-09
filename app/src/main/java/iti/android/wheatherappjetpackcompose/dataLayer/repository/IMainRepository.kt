package iti.android.wheatherappjetpackcompose.dataLayer.repository


import com.google.android.gms.maps.model.LatLng
import iti.android.wheatherappjetpackcompose.dataLayer.source.cash.*
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.HomeEntity
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.WeatherSuccessResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface IMainRepository {

    fun getCityName(latLng: LatLng): String
    fun checkInternetConnectivity(): Boolean
    fun getHome(): Flow<HomeEntity>
    suspend fun insertHome(home: HomeEntity)
    suspend fun deleteHome(home: HomeEntity)

    suspend fun getWeatherDetails(
        longitude: Double,
        latitude: Double,
        language: String,
    ): Response<WeatherSuccessResponse>


    fun getSharedSettings(): Flow<Settings>

    suspend fun updateTempraturSettings(temperature: Temperature)
    suspend fun updateWindSpeedSettings(windSpeed: WindSpeed)
    suspend fun updateLanguageSettings(language: Language)

    suspend fun updateLocationProviderSettings(locationProvider: LocationProvider)
    suspend fun updateUserLocationSettings(latLng: LatLng)
}