package iti.android.wheatherappjetpackcompose.dataLayer.repository


import com.google.android.gms.maps.model.LatLng
import iti.android.wheatherappjetpackcompose.dataLayer.source.cash.*
import kotlinx.coroutines.flow.Flow

interface ISettingsRepository {
    fun getSharedSettings(): Flow<Settings>

    suspend fun updateTempraturSettings(temperature: Temperature)
    suspend fun updateWindSpeedSettings(windSpeed: WindSpeed)
    suspend fun updateLanguageSettings(language: Language)

    suspend fun updateLocationProviderSettings(locationProvider: LocationProvider)
    suspend fun updateUserLocationSettings(latLng: LatLng)

}