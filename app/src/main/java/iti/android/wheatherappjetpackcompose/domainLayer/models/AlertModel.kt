package iti.android.wheatherappjetpackcompose.domainLayer.models

data class AlertModel(
    val id: Int? = null,
    var latitude: Double? = null,
    var longitude: Double? = null,
    var city: String? = null,
    var startTime: Long? = null,
    var endTime: Long? = null,
    var startDate: Long? = null,
    var endDate: Long? = null,
)
