package iti.android.wheatherappjetpackcompose.presentationLayer.screens.favorite

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.location.LocationManager
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.location.LocationManagerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.*
import iti.android.wheatherappjetpackcompose.utils.LocationState
import iti.android.wheatherappjetpackcompose.utils.LocationViewModel

@Composable
fun FavoriteScreen(navController: NavController? = null) {

    val viewModel: LocationViewModel = viewModel<LocationViewModel>()
    val activity = LocalContext.current as Activity
    viewModel.fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
    LocationScreen(viewModel, activity)
}

@Preview
@Composable
fun FavoriteScreenPreview() {
    FavoriteScreen()
}


@OptIn(ExperimentalPermissionsApi::class, ExperimentalAnimationApi::class)
@Composable
fun LocationScreen(viewModel: LocationViewModel, activity: Activity) {
    val locationPermissionState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    LaunchedEffect(locationPermissionState.allPermissionsGranted) {
        if (locationPermissionState.allPermissionsGranted) {
            if (locationEnabled(activity)) {
                viewModel.getCurrentLocation()
            } else {
                viewModel.locationState = LocationState.LocationDisabled
            }
        }
    }

    AnimatedContent(
        viewModel.locationState
    ) { state ->
        when (state) {
            is LocationState.NoPermission -> {
                Column {
                    Text("We need location permission to continue")
                    Button(onClick = { locationPermissionState.launchMultiplePermissionRequest() }) {
                        Text("Request permission")
                    }
                }
            }
            is LocationState.LocationDisabled -> {
                Column {
                    Text("We need location to continue")
                    Button(onClick = { requestLocationEnable(viewModel, activity) }) {
                        Text("Enable location")
                    }
                }
            }
            is LocationState.LocationLoading -> {
                Text("Loading Map")
            }
            is LocationState.Error -> {
                Column {
                    Text("Error fetching your location")
                    Button(onClick = { viewModel.getCurrentLocation() }) {
                        Text("Retry")
                    }
                }
            }
            is LocationState.LocationAvailable -> {
                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(state.location, 15f)
                }
                val mapUiSettings by remember { mutableStateOf(MapUiSettings()) }
                val mapProperties by remember { mutableStateOf(MapProperties(isMyLocationEnabled = true)) }

                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    uiSettings = mapUiSettings,
                    properties = mapProperties
                ) {
                    Marker(
                        state = rememberMarkerState(position = state.location)
                    )
                }
            }
        }
    }
}

private fun locationEnabled(activity: Activity): Boolean {
    val locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return LocationManagerCompat.isLocationEnabled(locationManager)
}

private fun requestLocationEnable(viewModel: LocationViewModel, activity: Activity) {
    val locationRequest = LocationRequest.create()
    val builder = LocationSettingsRequest
        .Builder()
        .addLocationRequest(locationRequest)
    val task = LocationServices
        .getSettingsClient(activity)
        .checkLocationSettings(builder.build())
        .addOnSuccessListener {
            if (it.locationSettingsStates?.isLocationPresent == true) {
                viewModel.getCurrentLocation()
            }
        }
        .addOnFailureListener {
            if (it is ResolvableApiException) {
                try {
                    it.startResolutionForResult(activity, 999)
                } catch (e: IntentSender.SendIntentException) {
                    e.printStackTrace()
                }
            }
        }


}

/*
val viewModel: LocationViewModel = viewModel<LocationViewModel>()
    val activity = LocalContext.current as Activity
    viewModel.getCurrentLocationData(activity)

    AnimatedContent(
        viewModel.locationState
    ) { state ->
        when (state) {
            is LocationState.LocationDisabled -> {
                Column {
                    Text("We need location to continue")
                    Button(onClick = { viewModel.openGPSLocation(activity) }) {
                        Text("Enable location")
                    }
                }
            }
            is LocationState.LocationLoading -> {
                Text("Loading Map")
            }
            is LocationState.Error -> {
                Column {
                    Text("Error fetching your location")
                    Button(onClick = { viewModel.getCurrentLocationData(activity) }) {
                        Text("Retry")
                    }
                }
            }
            is LocationState.LocationAvailable -> {
                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(state.location, 15f)
                }
                val mapUiSettings by remember { mutableStateOf(MapUiSettings()) }
                val mapProperties by remember { mutableStateOf(MapProperties(isMyLocationEnabled = true)) }

                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    uiSettings = mapUiSettings,
                    properties = mapProperties
                )
            }
        }
    }
 */