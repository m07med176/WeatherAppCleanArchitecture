package iti.android.wheatherappjetpackcompose.dataLayer.repository

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.google.android.gms.maps.model.LatLng
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.AlertEntity
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.FavoriteEntity
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.HomeEntity
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.WeatherSuccessResponse
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.ILocalDataSource
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.LocalDataSource
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.cash.*
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.room.RoomDB
import iti.android.wheatherappjetpackcompose.dataLayer.source.remote.IRemoteDataSource
import iti.android.wheatherappjetpackcompose.dataLayer.source.remote.RemoteDataSource
import iti.android.wheatherappjetpackcompose.dataLayer.source.remote.geoCoder.GeoCoderAPI
import iti.android.wheatherappjetpackcompose.dataLayer.source.remote.hasNetwork
import iti.android.wheatherappjetpackcompose.dataLayer.source.remote.retrofite.RetrofitInstance
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class RepositoryImpl(
    private val localDataSource: ILocalDataSource,
    private val remoteDataSource: IRemoteDataSource,
    override val context: Context,
) : RepositoryInterface {


    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: RepositoryImpl? = null

        fun getInstance(app: Application): RepositoryImpl {
            return INSTANCE ?: synchronized(this) {

                // Local Dependencies
                val room = RoomDB.invoke(app)
                val localDataSource =
                    LocalDataSource(room.alertDao(), room.homeDao(), room.favoriteDao())
                // Remote Dependencies
                val geoCoderAPI = GeoCoderAPI(app)
                val api = RetrofitInstance(app).api
                val remoteDataSource = RemoteDataSource(api, geoCoderAPI)

                RepositoryImpl(localDataSource, remoteDataSource, app)
            }
        }

        fun getInstance(app: Context): RepositoryImpl {
            return INSTANCE ?: synchronized(this) {

                // Local Dependencies
                val room = RoomDB.invoke(app)
                val localDataSource =
                    LocalDataSource(room.alertDao(), room.homeDao(), room.favoriteDao())
                // Remote Dependencies
                val geoCoderAPI = GeoCoderAPI(app)
                val api = RetrofitInstance(app).api
                val remoteDataSource = RemoteDataSource(api, geoCoderAPI)

                RepositoryImpl(localDataSource, remoteDataSource, app)
            }
        }
    }


    override fun getAlerts(): Flow<List<AlertEntity>> {
        return localDataSource.getAlerts()
    }

    override suspend fun insertAlert(entity: AlertEntity): Long {
        return localDataSource.insertAlert(entity)
    }

    override suspend fun deleteAlertByObject(entity: AlertEntity) {
        localDataSource.deleteAlertByObject(entity)
    }

    override suspend fun deleteAlert(id: Int) {
        localDataSource.deleteAlert(id)
    }

    override fun getAlert(id: Int): AlertEntity = localDataSource.getAlert(id)


    override fun getCityName(lat: Double, long: Double): String {
        return remoteDataSource.getCityName(lat, lat)
    }


    override fun getCityName(latLng: LatLng): String {
        return remoteDataSource.getCityName(latLng.latitude, latLng.longitude)
    }

    override fun checkInternetConnectivity(): Boolean = context.hasNetwork()

    override fun getHome(): Flow<HomeEntity> {
        return localDataSource.getHome()
    }

    override suspend fun insertHome(home: HomeEntity) {
        localDataSource.insertHome(home)
    }

    override suspend fun deleteHome(home: HomeEntity) {
        localDataSource.insertHome(home)
    }

    override suspend fun getWeatherDetails(
        longitude: Double,
        latitude: Double,
        language: String,
        units: String,
    ): Response<WeatherSuccessResponse> {
        return remoteDataSource.getWeatherDetails(
            longitude = longitude,
            latitude = latitude,
            language = language,
            units = units
        )
    }




    override fun getFavorites(): Flow<List<FavoriteEntity>> {
        return localDataSource.getFavorites()
    }

    override suspend fun insertFavorite(favorite: FavoriteEntity) {
        localDataSource.insertFavorite(favorite)
    }

    override suspend fun deleteFavorite(favorite: FavoriteEntity) {
        localDataSource.deleteFavorite(favorite)
    }


}