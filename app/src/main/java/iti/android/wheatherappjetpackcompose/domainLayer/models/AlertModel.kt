package iti.android.wheatherappjetpackcompose.domainLayer.models

import com.google.android.gms.maps.model.LatLng

data class AlertModel(
    val id: Int? = null,
    val location: LatLng? = null,
    var startTime: String? = null,
    var endTime: String? = null,
    var startDate: String? = null,
    var endDate: String? = null,
)
