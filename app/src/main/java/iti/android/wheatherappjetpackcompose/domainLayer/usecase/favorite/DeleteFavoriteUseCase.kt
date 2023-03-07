package iti.android.wheatherappjetpackcompose.domainLayer.usecase.favorite

import iti.android.wheatherappjetpackcompose.dataLayer.repository.IFavoriteRepository
import iti.android.wheatherappjetpackcompose.domainLayer.models.FavPlacesMapper
import iti.android.wheatherappjetpackcompose.domainLayer.models.FavPlacesModel

class DeleteFavoriteUseCase(private val repository: IFavoriteRepository) {
    suspend operator fun invoke(favPlacesModel: FavPlacesModel) {
        repository.deleteFavorite(FavPlacesMapper().entityFromMap(favPlacesModel))
    }
}