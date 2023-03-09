package iti.android.wheatherappjetpackcompose.dataLayer.repository

import com.google.android.gms.maps.model.LatLng
import iti.android.wheatherappjetpackcompose.dataLayer.source.cash.*
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl(
    private val cash: DataStoreManager,
) : ISettingsRepository {

    override fun getSharedSettings(): Flow<Settings> {
        return cash.getSharedSettings()
    }

    override suspend fun updateTempraturSettings(temperature: Temperature) {
        cash.updateTempraturSettings(temperature)
    }

    override suspend fun updateWindSpeedSettings(windSpeed: WindSpeed) {
        cash.updateWindSpeedSettings(windSpeed)
    }

    override suspend fun updateLanguageSettings(language: Language) {
        updateLanguageSettings(language)
    }

    override suspend fun updateLocationProviderSettings(locationProvider: LocationProvider) {
        cash.updateLocationProviderSettings(locationProvider)
    }

    override suspend fun updateUserLocationSettings(latLng: LatLng) {
        cash.updateUserLocationSettings(latLng)
    }


}