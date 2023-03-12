package iti.android.wheatherappjetpackcompose.dataLayer.source.local

import com.google.android.gms.maps.model.LatLng
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.AlertEntity
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.FavoriteEntity
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.HomeEntity
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.cash.*
import kotlinx.coroutines.flow.Flow

interface ILocalDataSource {

    // Alert DAO
    fun getAlerts(): Flow<List<AlertEntity>>

    suspend fun insertAlert(entity: AlertEntity)

    suspend fun deleteAlert(entity: AlertEntity)


    // Favorite DAO
    fun getFavorites(): Flow<List<FavoriteEntity>>

    suspend fun insertFavorite(favorite: FavoriteEntity)

    suspend fun deleteFavorite(favorite: FavoriteEntity)

    // Home DAO
    fun getHome(): Flow<HomeEntity>

    suspend fun insertHome(home: HomeEntity)

    suspend fun deleteHome(home: HomeEntity)

    // Cash
    fun getSharedSettings(): Flow<Settings>

    suspend fun updateTempraturSettings(temperature: Temperature)

    suspend fun updateWindSpeedSettings(windSpeed: WindSpeed)

    suspend fun updateLanguageSettings(language: Language)

    suspend fun updateLocationProviderSettings(locationProvider: LocationProvider)

    suspend fun updateUserLocationSettings(latLng: LatLng)


    // Shared Preference
    fun getPreferredLocale(): String

    fun setPreferredLocale(localeCode: String)
}