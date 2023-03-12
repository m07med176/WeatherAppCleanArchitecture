package iti.android.wheatherappjetpackcompose.domainLayer.usecase.home

import iti.android.wheatherappjetpackcompose.dataLayer.repository.RepositoryInterface
import iti.android.wheatherappjetpackcompose.domainLayer.models.WeatherDetailsCashMapper
import iti.android.wheatherappjetpackcompose.domainLayer.models.WeatherDetailsModel

class DeleteHomeUseCase(private val repository: RepositoryInterface) {
    suspend operator fun invoke(model: WeatherDetailsModel) {
        repository.deleteHome(WeatherDetailsCashMapper().entityFromMap(model))
    }
}