package iti.android.wheatherappjetpackcompose.dataLayer.source.remote.geoCoder

import android.content.Context
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import java.util.*

class GeoCoderAPI(val context: Context) {
    private val geoCoder = Geocoder(context, Locale.getDefault())

    fun getCityName(lat: Double, long: Double): String {
        return getDetailsFromGeoCoder(lat, long)
    }

    fun getCityName(latLng: LatLng): String {
        return getDetailsFromGeoCoder(latLng.latitude, latLng.longitude)
    }

    private fun getDetailsFromGeoCoder(lat: Double, long: Double): String {
        var cityName = ""
        geoCoder.getFromLocation(lat, long, 10)?.let { addresses ->
            if (addresses.isNotEmpty())
                cityName = addresses[0].adminArea
        }
        return cityName
    }


}