package iti.android.wheatherappjetpackcompose.domainLayer.models

import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.Current
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.WeatherSuccessResponse
import iti.android.wheatherappjetpackcompose.domainLayer.utils.EntityMapper
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
                clouds = currentEntity.clouds.toString(),
                dt = dateTimeConverterTimestampToString(currentEntity.dt ?: 0) ?: "",
                weather = currentEntity.weather,
                wind_gust = currentEntity.wind_gust.toString(),
                wind_deg = currentEntity.wind_deg.toString(),
                temp = currentEntity.temp.toString(),
                visibility = currentEntity.visibility.toString(),
                uvi = currentEntity.uvi.toString(),
                sunset = currentEntity.sunset.toString(),
                sunrise = currentEntity.sunrise.toString(),
                pressure = currentEntity.pressure.toString(),
                humidity = currentEntity.humidity.toString(),
                feels_like = currentEntity.feels_like.toString(),
                dew_point = currentEntity.dew_point.toString(),
                wind_speed = currentEntity.wind_speed.toString()


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
            current = null,
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