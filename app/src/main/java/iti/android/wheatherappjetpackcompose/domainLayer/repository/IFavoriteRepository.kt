package iti.android.wheatherappjetpackcompose.domainLayer.repository

import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.FavoriteEntity
import kotlinx.coroutines.flow.Flow

interface IFavoriteRepository {
    fun getFavorites(): Flow<List<FavoriteEntity>>
    suspend fun insertFavorite(favorite: FavoriteEntity)
    suspend fun deleteFavorite(favorite: FavoriteEntity)
}