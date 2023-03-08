package iti.android.wheatherappjetpackcompose.domainLayer.models

import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.Alert
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.Daily
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.Hourly
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.Weather


data class WeatherDetailsModel(
    val currentModel: CurrentModel? = null,
    val alert: List<Alert> = emptyList(),
    val daily: List<Daily> = emptyList(),
    val hourly: List<Hourly> = emptyList(),
    val lat: Double? = null,
    val lon: Double? = null,
    val timezone: String? = null,
    val timezone_offset: Int? = null,
    var cityName: String? = "",
)

data class CurrentModel(
    var clouds: Int? = null,
    val dew_point: Double? = null,
    val dt: String = "",
    val feels_like: Double? = null,
    val humidity: Int? = null,
    val pressure: Int? = null,
    val sunrise: Int? = null,
    val sunset: Int? = null,
    val temp: Double? = null,
    val uvi: Double? = null,
    val visibility: Int? = null,
    val weather: List<Weather> = emptyList(),
    val wind_deg: Int? = null,
    val wind_gust: Double? = null,
    val wind_speed: Int? = null,
)


