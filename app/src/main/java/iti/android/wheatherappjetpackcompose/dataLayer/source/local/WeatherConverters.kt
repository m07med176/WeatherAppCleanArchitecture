package iti.android.wheatherappjetpackcompose.dataLayer.source.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.WeatherSuccessResponse

/**
 * # Data Converter
 */
class WeatherConverters {
    @TypeConverter
    fun fromStringToWeather(weather: String?): WeatherSuccessResponse? {
        return weather?.let { Gson().fromJson(it, WeatherSuccessResponse::class.java) }
    }

    @TypeConverter
    fun fromWeatherToString(weather: WeatherSuccessResponse?): String? {
        return weather?.let { Gson().toJson(it) }
    }
}