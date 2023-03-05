package iti.android.wheatherappjetpackcompose.dataLayer.repository

import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.FavoriteEntity
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.FavoriteDao
import iti.android.wheatherappjetpackcompose.domainLayer.repository.IFavoriteRepository
import kotlinx.coroutines.flow.Flow

class FavoriteRepositoryImpl(private val dao: FavoriteDao) : IFavoriteRepository {
    override fun getFavorites(): Flow<List<FavoriteEntity>> {
        return dao.getFavorites()
    }

    override suspend fun insertFavorite(favorite: FavoriteEntity) {
        dao.insertFavorite(favorite)
    }

    override suspend fun deleteFavorite(favorite: FavoriteEntity) {
        dao.deleteFavorite(favorite)
    }
}