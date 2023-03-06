package iti.android.wheatherappjetpackcompose.domainLayer.usecase.favorite

import iti.android.wheatherappjetpackcompose.domainLayer.models.FavPlacesMapper
import iti.android.wheatherappjetpackcompose.domainLayer.models.FavPlacesModel
import iti.android.wheatherappjetpackcompose.domainLayer.repository.IFavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFavoritesUseCase(private val repository: IFavoriteRepository) {

    operator fun invoke(): Flow<List<FavPlacesModel>> {
        return repository.getFavorites().map { FavPlacesMapper().mapListFromEntityList(it) }
    }
}