package iti.android.wheatherappjetpackcompose

import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.AlertEntity
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.FavoriteEntity
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.HomeEntity
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.ILocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocalFakeDataSource(
    private var alertList: MutableList<AlertEntity> = mutableListOf<AlertEntity>(),
    private var favoriteList: MutableList<FavoriteEntity> = mutableListOf<FavoriteEntity>(),
    private var homeCash: HomeEntity,

    ) : ILocalDataSource {

    override fun getAlerts(): Flow<List<AlertEntity>> = flow {
        emit(alertList)
    }

    override suspend fun insertAlert(entity: AlertEntity): Long {
        alertList.add(entity)
        return alertList.lastIndex.toLong()
    }

    override suspend fun deleteAlertByObject(entity: AlertEntity) {
        alertList.remove(entity)
    }

    override suspend fun deleteAlert(id: Int) {
        alertList.remove(alertList[id])
    }

    override fun getAlert(id: Int): AlertEntity {
        return alertList[id]
    }

    override fun getFavorites(): Flow<List<FavoriteEntity>> = flow {
        emit(favoriteList)
    }

    override suspend fun insertFavorite(favorite: FavoriteEntity) {
        favoriteList.add(favorite)
    }

    override suspend fun deleteFavorite(favorite: FavoriteEntity) {
        favoriteList.remove(favorite)
    }

    override fun getHome(): Flow<HomeEntity> = flow {
        emit(homeCash)
    }

    override suspend fun insertHome(home: HomeEntity) {
        homeCash = home
    }

    override suspend fun deleteHome(home: HomeEntity) {
        // same behaviour of insert
        homeCash = home
    }
}