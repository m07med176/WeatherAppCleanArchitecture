package iti.android.wheatherappjetpackcompose.dataLayer.source.remote

import android.content.Context
import android.location.Geocoder
import java.util.*

class GeoCoderAPI(private val context: Context) {
    private val geoCoder = Geocoder(context, Locale.getDefault())

    fun getCityName(lat: Double, long: Double): String {
        var cityName: String = ""
        geoCoder.getFromLocation(lat, long, 1)?.let {
            cityName = it[0].locality
        }
        return cityName
    }
}