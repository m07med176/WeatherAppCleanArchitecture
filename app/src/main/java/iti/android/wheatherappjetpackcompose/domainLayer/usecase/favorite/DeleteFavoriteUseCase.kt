package iti.android.wheatherappjetpackcompose.domainLayer.usecase.favorite

import iti.android.wheatherappjetpackcompose.domainLayer.models.FavPlacesMapper
import iti.android.wheatherappjetpackcompose.domainLayer.models.FavPlacesModel
import iti.android.wheatherappjetpackcompose.domainLayer.repository.IFavoriteRepository

class DeleteFavoriteUseCase(private val repository: IFavoriteRepository) {
    suspend operator fun invoke(favPlacesModel: FavPlacesModel) {
        repository.deleteFavorite(FavPlacesMapper().entityFromMap(favPlacesModel))
    }
}