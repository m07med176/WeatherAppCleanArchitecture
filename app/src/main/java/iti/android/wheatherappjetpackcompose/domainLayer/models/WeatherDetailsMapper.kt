package iti.android.wheatherappjetpackcompose.domainLayer.models

import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.Current
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.WeatherSuccessResponse
import iti.android.wheatherappjetpackcompose.domainLayer.utils.*
import kotlin.streams.toList

class WeatherDetailsMapper : EntityMapper<WeatherSuccessResponse, WeatherDetailsModel> {
    override fun mapFromEntity(entity: WeatherSuccessResponse): WeatherDetailsModel {
        val currentEntity: Current = entity.current ?: Current()

        val dailyList = entity.daily?.stream()?.map { dailyEntity ->
            DailyModel(
                dt = dayConverterToString(dailyEntity.dt) ?: "",
                image = dailyEntity.weather.stream().map { weather -> iconConverter(weather.icon) }
                    .toList()[0],
                min = dailyEntity.temp.min.toString(),
                max = dailyEntity.temp.max.toString(),
                desc = dailyEntity.weather.stream().map { weather -> weather.description }
                    .toList()[0],

                )
        }?.toList() ?: emptyList()

        val hourlyList = entity.hourly?.stream()?.map { hourlyEntity ->
            HourlyModel(
                dt = timeConverterToString(hourlyEntity.dt) ?: "",
                image = hourlyEntity.weather.stream().map { weather -> iconConverter(weather.icon) }
                    .toList()[0],
                temp = hourlyEntity.temp.toString()
            )
        }?.toList() ?: emptyList()
        currentEntity.apply {
            if (weather.isNotEmpty()) {
                weather[0].icon = iconConverter(weather[0].icon)
            }
        }


        return WeatherDetailsModel(
            currentModel = CurrentModel(
                clouds = currentEntity.clouds.toString(),
                dt = dateTimeConverterTimestampToString(currentEntity.dt ?: 0.0) ?: "",
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
            alerts = entity.alert,
            daily = dailyList,
            hourly = hourlyList,
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
            alert = domainModel.alerts,
            daily = null,
            hourly = null,
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