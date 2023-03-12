package iti.android.wheatherappjetpackcompose.dataLayer.source.local

import com.google.android.gms.maps.model.LatLng
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.AlertEntity
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.FavoriteEntity
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.HomeEntity
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.cash.*
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.room.AlertDao
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.room.FavoriteDao
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.room.HomeDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(
    private val daoAlert: AlertDao,
    private val daoHome: HomeDao,
    private val daoFavorite: FavoriteDao,
    private val cash: DataStoreManager,
) : ILocalDataSource {

    // Alerts DAO
    override fun getAlerts(): Flow<List<AlertEntity>> = daoAlert.getAlerts()

    override suspend fun insertAlert(entity: AlertEntity) {
        daoAlert.insertAlert(entity)
    }

    override suspend fun deleteAlert(entity: AlertEntity) {
        daoAlert.deleteAlert(entity)
    }

    // Favorite DAO
    override fun getFavorites(): Flow<List<FavoriteEntity>> = daoFavorite.getFavorites()

    override suspend fun insertFavorite(favorite: FavoriteEntity) {
        daoFavorite.insertFavorite(favorite)
    }

    override suspend fun deleteFavorite(favorite: FavoriteEntity) {
        daoFavorite.deleteFavorite(favorite)
    }

    // Home DAO
    override fun getHome(): Flow<HomeEntity> = daoHome.getHome()

    override suspend fun insertHome(home: HomeEntity) {
        daoHome.insertHome(home)
    }

    override suspend fun deleteHome(home: HomeEntity) {
        daoHome.deleteHome(home)
    }

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
        cash.updateLanguageSettings(language)
    }

    override suspend fun updateLocationProviderSettings(locationProvider: LocationProvider) {
        cash.updateLocationProviderSettings(locationProvider)
    }

    override suspend fun updateUserLocationSettings(latLng: LatLng) {
        cash.updateUserLocationSettings(latLng)
    }
}