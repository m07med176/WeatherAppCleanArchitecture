package iti.android.wheatherappjetpackcompose.dataLayer.source.local.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import iti.android.wheatherappjetpackcompose.domainLayer.models.WeatherDetailsModel

/**
 * # Data Converter
 */
class WeatherConverters {
    @TypeConverter
    fun fromStringToWeather(weather: String?): WeatherDetailsModel? {
        return weather?.let { Gson().fromJson(it, WeatherDetailsModel::class.java) }
    }

    @TypeConverter
    fun fromWeatherToString(weather: WeatherDetailsModel?): String? {
        return weather?.let { Gson().toJson(it) }
    }
}