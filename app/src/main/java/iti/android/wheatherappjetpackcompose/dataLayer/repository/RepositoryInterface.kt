package iti.android.wheatherappjetpackcompose.dataLayer.repository

import com.google.android.gms.maps.model.LatLng
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.AlertEntity
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.FavoriteEntity
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.HomeEntity
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.WeatherSuccessResponse
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.cash.*
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RepositoryInterface {

    // Settings
    fun getSharedSettings(): Flow<Settings>

    suspend fun updateTempraturSettings(temperature: Temperature)
    suspend fun updateWindSpeedSettings(windSpeed: WindSpeed)
    suspend fun updateLanguageSettings(language: Language)

    suspend fun updateLocationProviderSettings(locationProvider: LocationProvider)
    suspend fun updateUserLocationSettings(latLng: LatLng)


    // GeoCoder
    fun getCityName(latLng: LatLng): String

    // CheckInternet Connectivity
    fun checkInternetConnectivity(): Boolean

    // Home Cash Weather
    fun getHome(): Flow<HomeEntity>
    suspend fun insertHome(home: HomeEntity)
    suspend fun deleteHome(home: HomeEntity)

    // Weather API
    suspend fun getWeatherDetails(
        longitude: Double,
        latitude: Double,
        language: String,
    ): Response<WeatherSuccessResponse>

    // Favorite
    fun getFavorites(): Flow<List<FavoriteEntity>>
    suspend fun insertFavorite(favorite: FavoriteEntity)
    suspend fun deleteFavorite(favorite: FavoriteEntity)

    // Alert
    fun getAlerts(): Flow<List<AlertEntity>>
    suspend fun insertAlert(entity: AlertEntity)
    suspend fun deleteAlert(entity: AlertEntity)


    // Shared Preferece
    fun getPreferredLocale(): String

    fun setPreferredLocale(localeCode: String)
}