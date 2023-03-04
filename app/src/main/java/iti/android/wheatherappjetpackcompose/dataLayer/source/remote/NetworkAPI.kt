package iti.android.wheatherappjetpackcompose.dataLayer.source.remote

import iti.android.wheatherappjetpackcompose.common.Constants
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.WeatherSuccessResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkAPI {
    @GET("onecall")
    suspend fun getWeatherDetails(
        @Query("appid") apiKey: String = Constants.API_KEY,
        @Query("exclude") exclude: String,
        @Query("lon") longitude: String,
        @Query("lat") latitude: String,
        @Query("units") units: String,

        ): Response<WeatherSuccessResponse>
}