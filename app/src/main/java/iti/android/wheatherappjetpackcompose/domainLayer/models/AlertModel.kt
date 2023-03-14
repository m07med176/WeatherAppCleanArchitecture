package iti.android.wheatherappjetpackcompose.domainLayer.models

data class AlertModel(
    val id: Int? = null,
    val description: String? = null,
    val end: Long? = null,
    val event: String? = null,
    val senderName: String? = null,
    val start: Long? = null,
    val tags: List<String> = emptyList(),

    var startTime: Long,
    var endTime: Long,
    var startDate: Long,
    var endDate: Long,
)
