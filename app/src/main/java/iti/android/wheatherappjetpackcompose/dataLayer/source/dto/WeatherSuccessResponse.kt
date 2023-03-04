package iti.android.wheatherappjetpackcompose.dataLayer.source.dto

data class WeatherSuccessResponse(
    val current: Current,
    val alert: List<Alert> = emptyList(),
    val daily: List<Daily> = emptyList(),
    val hourly: List<Hourly> = emptyList(),
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int,
)