package iti.android.wheatherappjetpackcompose.domainLayer.models

import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.Alert
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.Current
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.Daily
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.Hourly

data class WeatherDetailsModel(
    val current: Current? = null,
    val alert: List<Alert> = emptyList(),
    val daily: List<Daily> = emptyList(),
    val hourly: List<Hourly> = emptyList(),
    val lat: Double? = null,
    val lon: Double? = null,
    val timezone: String? = null,
    val timezone_offset: Int? = null,
)
