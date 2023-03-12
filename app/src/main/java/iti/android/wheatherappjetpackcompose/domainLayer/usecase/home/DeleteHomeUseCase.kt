package iti.android.wheatherappjetpackcompose.domainLayer.usecase.home

import iti.android.wheatherappjetpackcompose.dataLayer.repository.RepositoryInterface
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.WeatherSuccessResponse
import iti.android.wheatherappjetpackcompose.domainLayer.models.WeatherDetailsCashMapper

class DeleteHomeUseCase(private val repository: RepositoryInterface) {
    suspend operator fun invoke(model: WeatherSuccessResponse) {
        repository.deleteHome(WeatherDetailsCashMapper().entityFromMap(model))
    }
}