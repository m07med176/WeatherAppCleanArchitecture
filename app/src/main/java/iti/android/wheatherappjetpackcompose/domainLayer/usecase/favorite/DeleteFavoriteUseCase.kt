package iti.android.wheatherappjetpackcompose.domainLayer.usecase.favorite

import iti.android.wheatherappjetpackcompose.dataLayer.repository.RepositoryInterface
import iti.android.wheatherappjetpackcompose.domainLayer.models.FavPlacesMapper
import iti.android.wheatherappjetpackcompose.domainLayer.models.FavPlacesModel

class DeleteFavoriteUseCase(private val repository: RepositoryInterface) {
    suspend operator fun invoke(favPlacesModel: FavPlacesModel) {
        repository.deleteFavorite(FavPlacesMapper().entityFromMap(favPlacesModel))
    }
}