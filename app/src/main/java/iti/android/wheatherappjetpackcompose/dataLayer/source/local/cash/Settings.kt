package iti.android.wheatherappjetpackcompose.dataLayer.source.local.cash

import com.google.android.gms.maps.model.LatLng

data class Settings(
    var language: Language = Language.English,
    var temperature: Temperature = Temperature.Celsius,
    var windSpeed: WindSpeed = WindSpeed.Meter,
    var locationProvider: LocationProvider = LocationProvider.Nothing,
    var userLocation: LatLng? = null,

    )
