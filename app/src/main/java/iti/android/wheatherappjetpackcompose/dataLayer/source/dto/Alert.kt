package iti.android.wheatherappjetpackcompose.dataLayer.source.dto

import androidx.room.Ignore
import com.google.gson.annotations.SerializedName

data class Alert(
    val description: String,
    val end: Int,
    val event: String,
    @SerializedName("sender_name")
    val senderName: String,
    val start: Int,
    @Ignore
    val tags: List<String>,
)