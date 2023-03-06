package iti.android.wheatherappjetpackcompose.domainLayer.usecase.home

import iti.android.wheatherappjetpackcompose.domainLayer.models.WeatherDetailsCashMapper
import iti.android.wheatherappjetpackcompose.domainLayer.models.WeatherDetailsModel
import iti.android.wheatherappjetpackcompose.domainLayer.repository.IMainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetHomeUseCase(private val repository: IMainRepository) {

    operator fun invoke(): Flow<WeatherDetailsModel> {
        return repository.getHome().map {
            WeatherDetailsCashMapper().mapFromEntity(it)
        }
    }
}