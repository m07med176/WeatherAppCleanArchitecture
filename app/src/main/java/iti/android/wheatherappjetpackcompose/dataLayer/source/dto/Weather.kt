package iti.android.wheatherappjetpackcompose.dataLayer.source.dto

data class Weather(
    val description: String,
    var icon: String,
    val id: Int,
    val main: String,
)