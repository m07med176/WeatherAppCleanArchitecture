package iti.android.wheatherappjetpackcompose.domainLayer.models

import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.Current
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.WeatherSuccessResponse
import iti.android.wheatherappjetpackcompose.domainLayer.utils.EntityMapper
import iti.android.wheatherappjetpackcompose.domainLayer.utils.dateTimeConverterStringToTimestamp
import iti.android.wheatherappjetpackcompose.domainLayer.utils.dateTimeConverterTimestampToString

class WeatherDetailsMapper : EntityMapper<WeatherSuccessResponse, WeatherDetailsModel> {
    override fun mapFromEntity(entity: WeatherSuccessResponse): WeatherDetailsModel {
        val currentEntity: Current = entity.current ?: Current()

        currentEntity.apply {
            if (weather.isNotEmpty()) {
                weather[0].icon = "https://openweathermap.org/img/wn/${weather[0].icon}@2x.png"
            }
        }


        return WeatherDetailsModel(
            currentModel = CurrentModel(
                clouds = currentEntity.clouds ?: 0,
                dt = dateTimeConverterTimestampToString(currentEntity.dt ?: 0) ?: "",
                weather = currentEntity.weather,
                wind_gust = currentEntity.wind_gust ?: 0.0,
                wind_deg = currentEntity.wind_deg ?: 0,
                temp = currentEntity.temp ?: 0.0,
                visibility = currentEntity.visibility ?: 0,
                uvi = currentEntity.uvi ?: 0.0,
                sunset = currentEntity.sunset ?: 0,
                sunrise = currentEntity.sunrise ?: 0,
                pressure = currentEntity.pressure ?: 0,
                humidity = currentEntity.humidity ?: 0,
                feels_like = currentEntity.feels_like ?: 0.0,
                dew_point = currentEntity.dew_point ?: 0.0,
                wind_speed = currentEntity.wind_speed ?: 0


            ),
            alert = entity.alert,
            daily = entity.daily,
            hourly = entity.hourly,
            lat = entity.lat,
            lon = entity.lon,
            timezone = entity.timezone,
            timezone_offset = entity.timezone_offset
        )
    }

    override fun entityFromMap(domainModel: WeatherDetailsModel): WeatherSuccessResponse {
        val currentModel: CurrentModel = domainModel.currentModel ?: CurrentModel()
        return WeatherSuccessResponse(
            current = Current(
                clouds = currentModel.clouds ?: 0,
                dt = dateTimeConverterStringToTimestamp(currentModel.dt),
                weather = currentModel.weather,
                wind_gust = currentModel.wind_gust ?: 0.0,
                wind_deg = currentModel.wind_deg ?: 0,
                temp = currentModel.temp ?: 0.0,
                visibility = currentModel.visibility ?: 0,
                uvi = currentModel.uvi ?: 0.0,
                sunset = currentModel.sunset ?: 0,
                sunrise = currentModel.sunrise ?: 0,
                pressure = currentModel.pressure ?: 0,
                humidity = currentModel.humidity ?: 0,
                feels_like = currentModel.feels_like ?: 0.0,
                dew_point = currentModel.dew_point ?: 0.0,
                wind_speed = currentModel.wind_speed ?: 0


            ),
            alert = domainModel.alert,
            daily = domainModel.daily,
            hourly = domainModel.hourly,
            lat = domainModel.lat,
            lon = domainModel.lon,
            timezone = domainModel.timezone,
            timezone_offset = domainModel.timezone_offset
        )
    }

    override fun mapListFromEntityList(entityList: List<WeatherSuccessResponse>): List<WeatherDetailsModel> {
        return entityList.map { mapFromEntity(it) }
    }

    override fun entityListFromMapList(domainModelList: List<WeatherDetailsModel>): List<WeatherSuccessResponse> {
        return domainModelList.map { entityFromMap(it) }
    }


}