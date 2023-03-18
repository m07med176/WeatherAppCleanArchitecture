package iti.android.wheatherappjetpackcompose.dataLayer.repository

import android.content.Context
import com.google.android.gms.maps.model.LatLng
import iti.android.wheatherappjetpackcompose.LocalFakeDataSource
import iti.android.wheatherappjetpackcompose.RemoteFakeDataSource
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.*
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.ILocalDataSource
import iti.android.wheatherappjetpackcompose.dataLayer.source.remote.IRemoteDataSource
import iti.android.wheatherappjetpackcompose.domainLayer.models.WeatherDetailsMapper
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class FakeRepository(
    override val context: Context,
    val localDataSource: ILocalDataSource = LocalFakeDataSource(
        alertList = mutableListOf(
            AlertEntity(
                id = 1,
                endDate = 8562389652,
                startDate = 8562345623,
                startTime = 86535623,
                endTime = 854785,
                city = "dfs",
                longitude = 52.5856,
                latitude = 42.2445,
            ),
            AlertEntity(
                id = 1,
                endDate = 8562389652,
                startDate = 8562345623,
                startTime = 86535623,
                endTime = 854785,
                city = "dfs",
                longitude = 52.5856,
                latitude = 42.2445,
            ), AlertEntity(
                id = 1,
                endDate = 8562389652,
                startDate = 8562345623,
                startTime = 86535623,
                endTime = 854785,
                city = "dfs",
                longitude = 52.5856,
                latitude = 42.2445,
            )
        ),
        favoriteList = mutableListOf(
            FavoriteEntity(city = "Mohamed", latitude = 0.252, longitude = 0.532),
            FavoriteEntity(city = "Mohamed", latitude = 0.252, longitude = 0.532),
            FavoriteEntity(city = "Mohamed", latitude = 0.252, longitude = 0.532)
        ),
        homeCash = HomeEntity(
            content = WeatherDetailsMapper().mapFromEntity(
                WeatherSuccessResponse(
                    lat = 0.256532,
                    lon = 0.065535,
                    timezone = "56352232",
                    timezone_offset = 55,
                    current = Current(
                        clouds = 54.toDouble(),
                        dew_point = 0.5455,
                        dt = 51.toDouble(),
                        feels_like = 5.24,
                        humidity = 5635.toDouble(),
                        pressure = 545.toDouble(),
                        sunrise = 54645.toDouble(),
                        sunset = 564.toDouble(),
                        temp = 0.224,
                        uvi = 0.2454,
                        visibility = 45.toDouble(),
                        weather = emptyList(),
                        wind_deg = 554.toDouble(),
                        wind_gust = 0.25,
                        wind_speed = 564.toDouble()
                    )
                )
            )
        )
    ),
    val remoteDataSource: IRemoteDataSource = RemoteFakeDataSource(
        WeatherSuccessResponse(
            lat = 0.256532,
            lon = 0.065535,
            timezone = "56352232",
            timezone_offset = 55,
            current = Current(
                clouds = 54.toDouble(),
                dew_point = 0.5455,
                dt = 51.toDouble(),
                feels_like = 5.24,
                humidity = 5635.toDouble(),
                pressure = 545.toDouble(),
                sunrise = 54645.toDouble(),
                sunset = 564.toDouble(),
                temp = 0.224,
                uvi = 0.2454,
                visibility = 45.toDouble(),
                weather = emptyList(),
                wind_deg = 554.toDouble(),
                wind_gust = 0.25,
                wind_speed = 564.toDouble()
            )
        )
    ),

    ) : RepositoryInterface {

    override fun getAlerts(): Flow<List<AlertEntity>> = localDataSource.getAlerts()

    override suspend fun insertAlert(entity: AlertEntity): Long =
        localDataSource.insertAlert(entity)

    override suspend fun deleteAlertByObject(entity: AlertEntity) {
        localDataSource.deleteAlertByObject(entity)
    }

    override suspend fun deleteAlert(id: Int) {
        localDataSource.deleteAlert(id)
    }

    override fun getAlert(id: Int): AlertEntity = localDataSource.getAlert(id)

    override fun getFavorites(): Flow<List<FavoriteEntity>> = localDataSource.getFavorites()

    override suspend fun insertFavorite(favorite: FavoriteEntity) {
        localDataSource.insertFavorite(favorite)
    }

    override suspend fun deleteFavorite(favorite: FavoriteEntity) {
        localDataSource.deleteFavorite(favorite)
    }

    override fun getHome(): Flow<HomeEntity> = localDataSource.getHome()

    override suspend fun insertHome(home: HomeEntity) {
        localDataSource.insertHome(home)
    }

    override suspend fun deleteHome(home: HomeEntity) {
        // same behaviour of insert
        localDataSource.deleteHome(home)
    }

    override suspend fun getWeatherDetails(
        longitude: Double,
        latitude: Double,
        language: String,
        units: String,
    ): Response<WeatherSuccessResponse> =
        remoteDataSource.getWeatherDetails(longitude, latitude, language, units)

    override fun getCityName(lat: Double, long: Double): String =
        remoteDataSource.getCityName(lat, long)

    override fun getCityName(latLng: LatLng): String = remoteDataSource.getCityName(latLng)

    override fun checkInternetConnectivity(): Boolean = remoteDataSource.checkInternetConnectivity()
}