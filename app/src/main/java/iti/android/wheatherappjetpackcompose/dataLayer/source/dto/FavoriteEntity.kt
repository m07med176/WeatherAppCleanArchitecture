package iti.android.wheatherappjetpackcompose.dataLayer.source.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val content: WeatherSuccessResponse,
)
