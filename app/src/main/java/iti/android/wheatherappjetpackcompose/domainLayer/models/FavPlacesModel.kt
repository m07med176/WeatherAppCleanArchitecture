package iti.android.wheatherappjetpackcompose.domainLayer.models

import com.google.android.gms.maps.model.LatLng
import java.io.Serializable

data class FavPlacesModel(
    val id: Int? = null,
    val city: String = "",
    val location: LatLng,
) : Serializable
