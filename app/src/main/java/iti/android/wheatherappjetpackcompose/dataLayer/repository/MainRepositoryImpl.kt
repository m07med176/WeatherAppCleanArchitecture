package iti.android.wheatherappjetpackcompose.dataLayer.repository

import com.google.android.gms.maps.model.LatLng
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
        exclude: String?,
        longitude: Double,
        latitude: Double,
        units: String,
    ): Response<WeatherSuccessResponse> {
        return api.getWeatherDetails(
            exclude = exclude,
            longitude = longitude,
            latitude = latitude,
            units = units
        )
    }
}