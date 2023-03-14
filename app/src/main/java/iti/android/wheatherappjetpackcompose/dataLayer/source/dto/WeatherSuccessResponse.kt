package iti.android.wheatherappjetpackcompose.dataLayer.source.dto

data class WeatherSuccessResponse(
    val current: Current? = null,
    val alert: List<Alert> = emptyList(),
    val daily: List<Daily>? = emptyList(),
    val hourly: List<Hourly>? = emptyList(),
    val lat: Double? = null,
    val lon: Double? = null,
    val timezone: String? = null,
    val timezone_offset: Int? = null,
)