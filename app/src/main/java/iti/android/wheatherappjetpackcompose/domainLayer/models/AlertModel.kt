package iti.android.wheatherappjetpackcompose.domainLayer.models

data class AlertModel(
    val id: Int? = null,
    val description: String?,
    val end: Int?,
    val event: String?,
    val senderName: String?,
    val start: Int?,
    val tags: List<String> = emptyList(),
)
