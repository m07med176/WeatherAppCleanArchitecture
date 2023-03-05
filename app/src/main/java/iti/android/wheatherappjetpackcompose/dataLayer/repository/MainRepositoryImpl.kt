package iti.android.wheatherappjetpackcompose.dataLayer.repository

import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.HomeEntity
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.WeatherSuccessResponse
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.HomeDao
import iti.android.wheatherappjetpackcompose.dataLayer.source.remote.NetworkAPI
import iti.android.wheatherappjetpackcompose.domainLayer.repository.IMainRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class MainRepositoryImpl(
    private val dao: HomeDao,
    private val api: NetworkAPI,
) : IMainRepository {
    override fun getHome(): Flow<HomeEntity> {
        return dao.getHome()
    }

    override suspend fun insertHome(home: HomeEntity) {
        dao.insertHome(home)
    }

    override suspend fun deleteHome(home: HomeEntity) {
        dao.insertHome(home)
    }

    override suspend fun getWeatherDetails(
        exclude: String?,
        longitude: Double,
        latitude: Double,
        units: String?,
    ): Response<WeatherSuccessResponse> {
        return api.getWeatherDetails(
            exclude = exclude,
            longitude = longitude,
            latitude = latitude,
            units = units
        )
    }
}