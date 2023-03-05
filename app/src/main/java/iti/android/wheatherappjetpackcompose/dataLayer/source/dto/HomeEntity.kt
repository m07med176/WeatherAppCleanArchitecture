package iti.android.wheatherappjetpackcompose.dataLayer.source.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "home_table")
data class HomeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val content: WeatherSuccessResponse,
)
