package iti.android.wheatherappjetpackcompose.dataLayer.source.dto

import com.google.gson.annotations.SerializedName

data class WeatherSuccessResponse(
    val current: Current? = null,
    @SerializedName("alerts") var alerts: List<Alerts>? = null,
    val daily: List<Daily>? = emptyList(),
    val hourly: List<Hourly>? = emptyList(),
    val lat: Double? = null,
    val lon: Double? = null,
    val timezone: String? = null,
    val timezone_offset: Int? = null,
)