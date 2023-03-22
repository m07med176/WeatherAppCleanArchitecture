package iti.android.wheatherappjetpackcompose.dataLayer.source.local.room

import androidx.room.TypeConverter


const val SPLITTER = "$$"

class StringListConverters {
    @TypeConverter
    fun fromStringToStringList(data: String?): List<String>? {
        return data?.split(SPLITTER)
    }

    @TypeConverter
    fun fromStringListToString(data: List<String>?): String? {
        return data?.let { it.joinToString { SPLITTER } }
    }
}