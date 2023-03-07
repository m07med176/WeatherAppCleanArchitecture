package iti.android.wheatherappjetpackcompose.domainLayer.usecase.home

import iti.android.wheatherappjetpackcompose.dataLayer.repository.IMainRepository
import iti.android.wheatherappjetpackcompose.domainLayer.models.WeatherDetailsCashMapper
import iti.android.wheatherappjetpackcompose.domainLayer.models.WeatherDetailsModel

class DeleteHomeUseCase(private val repository: IMainRepository) {
    suspend operator fun invoke(model: WeatherDetailsModel) {
        repository.deleteHome(WeatherDetailsCashMapper().entityFromMap(model))
    }
}