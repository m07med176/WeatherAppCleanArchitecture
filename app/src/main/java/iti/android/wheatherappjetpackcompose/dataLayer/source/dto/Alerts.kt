package iti.android.wheatherappjetpackcompose.dataLayer.source.dto

import com.google.gson.annotations.SerializedName

data class Alerts(
    val description: String?,
    val end: Long?,
    val event: String?,
    @SerializedName("sender_name")
    val senderName: String?,
    val start: Long?,
    val tags: List<String> = emptyList(),
)