package iti.android.wheatherappjetpackcompose.utils

import android.content.Context
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException
import java.util.*

private const val TAG = "MapUtils"

class MapUtils {
    companion object {
        fun addMarker(
            googleMap: GoogleMap,
            latLng: LatLng,
            markerColor: Float = BitmapDescriptorFactory.HUE_RED,
            zoom: Float = 15f,
            title: String = "",
        ) {
            try {
                val cLocation = LatLng(latLng.latitude, latLng.longitude)
                val snippet = String.format(
                    Locale.getDefault(),
                    "Lat: %1$.5f, Long: %2$.5f",
                    latLng.latitude,
                    latLng.longitude
                )
                googleMap.addMarker(
                    MarkerOptions()
                        .position(cLocation)
                        .title(title)
                        .snippet(snippet)
                        .icon(BitmapDescriptorFactory.defaultMarker(markerColor))
                )
                /**
                 * ## Zoom Levels
                 * - 1: World
                 * - 5: Landmass/continent
                 * - 10: City
                 * - 15: Streets
                 * - 20: Buildings
                 */
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }


        fun setImageGroundOverlay(map: GoogleMap, latLng: LatLng) {
//            val overlaySize = 100f
//            val androidOverlay = GroundOverlayOptions()
//                .image(BitmapDescriptorFactory.fromResource(R.drawable.iti))
//                .position(latLng,overlaySize)
//            map.addGroundOverlay(androidOverlay)
        }

        fun onPOIClicked(map: GoogleMap) {
            map.setOnPoiClickListener { poi ->
                val marker = map.addMarker(
                    MarkerOptions()
                        .position(poi.latLng)
                        .title(poi.name)
                )
                marker!!.showInfoWindow()
            }
        }

        fun setMapStyle(map: GoogleMap, context: Context) {
//            try {
//                val success = map.setMapStyle(
//                    MapStyleOptions.loadRawResourceStyle(
//                        context,
//                        R.raw.map_style
//                    )
//                )
//                if (!success) {
//                    Log.e(TAG, "Style parsing failed.")
//                }
//            }catch (e: Resources.NotFoundException) {
//                Log.e(TAG, "Can't find style. Error: ", e)
//            }
        }

    }
}
