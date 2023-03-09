package iti.android.wheatherappjetpackcompose.domainLayer.usecase.home

import iti.android.wheatherappjetpackcompose.dataLayer.repository.IMainRepository
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.WeatherSuccessResponse
import iti.android.wheatherappjetpackcompose.domainLayer.models.WeatherDetailsCashMapper

class DeleteHomeUseCase(private val repository: IMainRepository) {
    suspend operator fun invoke(model: WeatherSuccessResponse) {
        repository.deleteHome(WeatherDetailsCashMapper().entityFromMap(model))
    }
}