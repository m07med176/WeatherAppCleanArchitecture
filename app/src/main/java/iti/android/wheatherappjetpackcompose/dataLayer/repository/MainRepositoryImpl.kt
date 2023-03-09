package iti.android.wheatherappjetpackcompose.dataLayer.repository

import com.google.android.gms.maps.model.LatLng
import iti.android.wheatherappjetpackcompose.dataLayer.source.cash.*
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.HomeEntity
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.WeatherSuccessResponse
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.HomeDao
import iti.android.wheatherappjetpackcompose.dataLayer.source.remote.GeoCoderAPI
import iti.android.wheatherappjetpackcompose.dataLayer.source.remote.NetworkAPI
import iti.android.wheatherappjetpackcompose.dataLayer.source.remote.RetrofitInstance
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class MainRepositoryImpl(
    private val dao: HomeDao,
    private val retrofitInstance: RetrofitInstance,
    private val geoCoderAPI: GeoCoderAPI,
    private val cash: DataStoreManager,
) : IMainRepository {

    private var api: NetworkAPI = retrofitInstance.retrofit.create(NetworkAPI::class.java)
    override fun getCityName(latLng: LatLng): String {
        return geoCoderAPI.getCityName(latLng.latitude, latLng.longitude)
    }

    override fun checkInternetConnectivity(): Boolean = retrofitInstance.hasNetwork()

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
        longitude: Double,
        latitude: Double,
        language: String,
    ): Response<WeatherSuccessResponse> {
        return api.getWeatherDetails(
            longitude = longitude,
            latitude = latitude,
            language = language
        )
    }


    override fun getSharedSettings(): Flow<Settings> {
        return cash.getSharedSettings()
    }

    override suspend fun updateTempraturSettings(temperature: Temperature) {
        cash.updateTempraturSettings(temperature)
    }

    override suspend fun updateWindSpeedSettings(windSpeed: WindSpeed) {
        cash.updateWindSpeedSettings(windSpeed)
    }

    override suspend fun updateLanguageSettings(language: Language) {
        cash.updateLanguageSettings(language)
    }

    override suspend fun updateLocationProviderSettings(locationProvider: LocationProvider) {
        cash.updateLocationProviderSettings(locationProvider)
    }

    override suspend fun updateUserLocationSettings(latLng: LatLng) {
        cash.updateUserLocationSettings(latLng)
    }
}