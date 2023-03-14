package iti.android.wheatherappjetpackcompose.dataLayer.source.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alert_table")
data class AlertEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var latitude: Double? = null,
    var longitude: Double? = null,
    var city: String? = null,
    var startTime: Long? = null,
    var endTime: Long? = null,
    var startDate: Long? = null,
    var endDate: Long? = null,
)
