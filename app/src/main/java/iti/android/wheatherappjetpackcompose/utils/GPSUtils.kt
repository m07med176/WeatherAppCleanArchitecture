package iti.android.wheatherappjetpackcompose.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.provider.Settings
import android.widget.Toast
import androidx.core.location.LocationManagerCompat
import com.google.android.gms.location.*
import iti.android.wheatherappjetpackcompose.utils.PermissionUtils.Companion.permissionAction

class GPSUtils {
    companion object {
        const val PERMISSION_LOCATION_REQUEST = 254
        val LOCATION_PERMISSIONS_LIST = listOf(
            Permissions(Manifest.permission.ACCESS_FINE_LOCATION, PERMISSION_LOCATION_REQUEST),
            Permissions(Manifest.permission.ACCESS_COARSE_LOCATION, PERMISSION_LOCATION_REQUEST),
        )


        private fun openGPSLocation(activity: Activity, callFuction: () -> Unit) {
            LOCATION_PERMISSIONS_LIST.permissionAction(activity) {
                if (isLocationEnabled(activity)) {
                    // Logic on location enabled
                    callFuction()
                } else {
                    Toast.makeText(activity, "Please Turn On Location", Toast.LENGTH_LONG).show()
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    activity.startActivity(intent)
                }
            }

        }

        private fun isLocationEnabled(activity: Activity): Boolean {
            val locationManager =
                activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return LocationManagerCompat.isLocationEnabled(locationManager)
        }

        @SuppressLint("MissingPermission")
        fun requestNewLocationData(
            activity: Activity,
            onLocationResult: (location: Location) -> Unit,
        ) {
            val mFusedLocationProviderClient: FusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(activity)
            val mLocationRequest = LocationRequest()
            mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            mLocationRequest.interval = 0
            mFusedLocationProviderClient.lastLocation.addOnSuccessListener { lastLocation ->
                onLocationResult(lastLocation)
            }
        }

        fun getLastLocation(activity: Activity, onLocationResult: (location: Location) -> Unit) {
            openGPSLocation(activity) {
                requestNewLocationData(activity, onLocationResult)
            }
        }
    }
}