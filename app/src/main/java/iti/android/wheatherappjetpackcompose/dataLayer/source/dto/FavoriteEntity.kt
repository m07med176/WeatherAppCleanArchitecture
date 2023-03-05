package iti.android.wheatherappjetpackcompose.dataLayer.source.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_table")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    val city: String,
    val latitude: Double,
    val longitude: Double,

    )
