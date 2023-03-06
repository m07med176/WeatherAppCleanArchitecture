package iti.android.wheatherappjetpackcompose.domainLayer.usecase.home

import iti.android.wheatherappjetpackcompose.domainLayer.models.WeatherDetailsCashMapper
import iti.android.wheatherappjetpackcompose.domainLayer.models.WeatherDetailsModel
import iti.android.wheatherappjetpackcompose.domainLayer.repository.IMainRepository

class DeleteHomeUseCase(private val repository: IMainRepository) {
    suspend operator fun invoke(model: WeatherDetailsModel) {
        repository.deleteHome(WeatherDetailsCashMapper().entityFromMap(model))
    }
}