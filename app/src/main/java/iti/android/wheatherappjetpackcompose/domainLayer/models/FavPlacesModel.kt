package iti.android.wheatherappjetpackcompose.domainLayer.models

import com.google.android.gms.maps.model.LatLng

data class FavPlacesModel(
    val id: Int? = null,
    val city: String,
    val location: LatLng,
)
