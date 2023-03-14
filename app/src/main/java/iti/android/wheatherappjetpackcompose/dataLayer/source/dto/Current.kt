package iti.android.wheatherappjetpackcompose.dataLayer.source.dto

data class Current(
    var clouds: Double? = null,
    val dew_point: Double? = null,
    val dt: Long? = null,
    val feels_like: Double? = null,
    val humidity: Double? = null,
    val pressure: Double? = null,
    val sunrise: Double? = null,
    val sunset: Double? = null,
    val temp: Double? = null,
    val uvi: Double? = null,
    val visibility: Double? = null,
    val weather: List<Weather> = emptyList(),
    val wind_deg: Double? = null,
    val wind_gust: Double? = null,
    val wind_speed: Double? = null,
)