package iti.android.wheatherappjetpackcompose.dataLayer.source.dto

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alert_table")
data class AlertEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @Embedded val content: Alert,
)
