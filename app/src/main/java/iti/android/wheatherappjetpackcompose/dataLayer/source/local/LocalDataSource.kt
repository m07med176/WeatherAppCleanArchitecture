package iti.android.wheatherappjetpackcompose.dataLayer.source.local

import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.AlertEntity
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.FavoriteEntity
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.HomeEntity
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.room.AlertDao
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.room.FavoriteDao
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.room.HomeDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(
    private val daoAlert: AlertDao,
    private val daoHome: HomeDao,
    private val daoFavorite: FavoriteDao,
) : ILocalDataSource {

    // Alerts DAO
    override fun getAlerts(): Flow<List<AlertEntity>> = daoAlert.getAlerts()

    override suspend fun insertAlert(entity: AlertEntity): Long = daoAlert.insertAlert(entity)

    override suspend fun deleteAlertByObject(entity: AlertEntity) {
        daoAlert.deleteAlertByObject(entity)
    }

    override suspend fun deleteAlert(id: Int) {
        daoAlert.deleteAlert(id)
    }

    override fun getAlert(id: Int): AlertEntity {
        return daoAlert.getAlert(id)
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
}