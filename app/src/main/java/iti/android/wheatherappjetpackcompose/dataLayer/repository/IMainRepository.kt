package iti.android.wheatherappjetpackcompose.dataLayer.repository


import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.HomeEntity
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.WeatherSuccessResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface IMainRepository {

    fun checkInternetConnectivity(): Boolean
    fun getHome(): Flow<HomeEntity>
    suspend fun insertHome(home: HomeEntity)
    suspend fun deleteHome(home: HomeEntity)

    suspend fun getWeatherDetails(
        exclude: String? = null,
        longitude: Double,
        latitude: Double,
        units: String? = null,
    ): Response<WeatherSuccessResponse>
}