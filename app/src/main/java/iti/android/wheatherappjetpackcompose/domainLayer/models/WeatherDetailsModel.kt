package iti.android.wheatherappjetpackcompose.domainLayer.models

import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.Alert
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.Weather


data class WeatherDetailsModel(
    val currentModel: CurrentModel? = null,
    val alerts: List<Alert> = emptyList(),
    val daily: List<DailyModel> = emptyList(),
    val hourly: List<HourlyModel> = emptyList(),
    val lat: Double? = null,
    val lon: Double? = null,
    val timezone: String? = null,
    val timezone_offset: Int? = null,
    var cityName: String? = "",
)

data class CurrentModel(
    var clouds: String = "",
    val dew_point: String = "",
    val dt: String = "",
    val feels_like: String = "",
    val humidity: String = "",
    val pressure: String = "",
    val sunrise: String = "",
    val sunset: String = "",
    val temp: String = "",
    val uvi: String = "",
    val visibility: String = "",
    val weather: List<Weather> = emptyList(),
    val wind_deg: String = "",
    val wind_gust: String = "",
    val wind_speed: String = "",
)


data class HourlyModel(
    val dt: String,
    val image: String,
    val temp: String,

    )


data class DailyModel(
    var dt: String = "",
    var image: String = "",
    var max: String = "",
    var min: String = "",
    var desc: String = "",

    )