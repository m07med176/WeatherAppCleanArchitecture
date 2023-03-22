package iti.android.wheatherappjetpackcompose.dataLayer.source.local

import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.AlertEntity
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.FavoriteEntity
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.HomeEntity
import kotlinx.coroutines.flow.Flow

interface ILocalDataSource {

    // Alert DAO
    fun getAlerts(): Flow<List<AlertEntity>>

    suspend fun insertAlert(entity: AlertEntity): Long

    suspend fun deleteAlertByObject(entity: AlertEntity)

    suspend fun deleteAlert(id: Int)

    fun getAlert(id: Int): AlertEntity

    // Favorite DAO
    fun getFavorites(): Flow<List<FavoriteEntity>>

    suspend fun insertFavorite(favorite: FavoriteEntity)

    suspend fun deleteFavorite(favorite: FavoriteEntity)

    // Home DAO
    fun getHome(): Flow<HomeEntity>

    suspend fun insertHome(home: HomeEntity)

    suspend fun deleteHome(home: HomeEntity)
}