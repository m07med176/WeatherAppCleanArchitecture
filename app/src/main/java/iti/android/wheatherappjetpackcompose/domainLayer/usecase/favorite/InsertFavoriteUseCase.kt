package iti.android.wheatherappjetpackcompose.domainLayer.usecase.favorite

import iti.android.wheatherappjetpackcompose.dataLayer.repository.RepositoryInterface
import iti.android.wheatherappjetpackcompose.domainLayer.models.FavPlacesMapper
import iti.android.wheatherappjetpackcompose.domainLayer.models.FavPlacesModel

class InsertFavoriteUseCase(private val repository: RepositoryInterface) {
    suspend operator fun invoke(favPlacesModel: FavPlacesModel) {
        val cityName = repository.getCityName(favPlacesModel.location)
        val favPlace = favPlacesModel.copy(
            city = cityName
        )
        repository.insertFavorite(FavPlacesMapper().entityFromMap(favPlace))
    }
}