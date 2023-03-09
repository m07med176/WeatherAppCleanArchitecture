package iti.android.wheatherappjetpackcompose.domainLayer.models

import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.HomeEntity
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.WeatherSuccessResponse
import iti.android.wheatherappjetpackcompose.domainLayer.utils.EntityMapper

class WeatherDetailsCashMapper : EntityMapper<HomeEntity, WeatherSuccessResponse> {
    override fun mapFromEntity(entity: HomeEntity): WeatherSuccessResponse {
        return WeatherSuccessResponse(
            current = entity.content.current,
            alert = entity.content.alert,
            daily = entity.content.daily,
            hourly = entity.content.hourly,
            lat = entity.content.lat,
            lon = entity.content.lon,
            timezone = entity.content.timezone,
            timezone_offset = entity.content.timezone_offset
        )
    }

    override fun entityFromMap(domainModel: WeatherSuccessResponse): HomeEntity {
        return HomeEntity(
            content = WeatherSuccessResponse(
                current = domainModel.current,
                alert = domainModel.alert,
                daily = domainModel.daily,
                hourly = domainModel.hourly,
                lat = domainModel.lat,
                lon = domainModel.lon,
                timezone = domainModel.timezone,
                timezone_offset = domainModel.timezone_offset
            )
        )

    }

    override fun mapListFromEntityList(entityList: List<HomeEntity>): List<WeatherSuccessResponse> {
        return entityList.map { mapFromEntity(it) }
    }

    override fun entityListFromMapList(domainModelList: List<WeatherSuccessResponse>): List<HomeEntity> {
        return domainModelList.map { entityFromMap(it) }
    }
}