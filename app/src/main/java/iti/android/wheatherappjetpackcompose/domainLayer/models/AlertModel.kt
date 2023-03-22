package iti.android.wheatherappjetpackcompose.domainLayer.models

data class AlertModel(
    val id: Int? = null,
    var latitude: Double? = null,
    var longitude: Double? = null,
    var city: String? = null,
    var startTime: String? = null,
    var endTime: String? = null,
    var startDate: String? = null,
    var endDate: String? = null,
)
