package iti.android.wheatherappjetpackcompose.dataLayer.source.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import iti.android.wheatherappjetpackcompose.domainLayer.models.WeatherDetailsModel

@Entity(tableName = "home_table")
data class HomeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val content: WeatherDetailsModel,
)
