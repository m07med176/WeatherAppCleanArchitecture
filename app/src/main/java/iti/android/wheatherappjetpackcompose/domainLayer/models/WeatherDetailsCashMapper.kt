package iti.android.wheatherappjetpackcompose.domainLayer.models

import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.HomeEntity
import iti.android.wheatherappjetpackcompose.domainLayer.utils.EntityMapper

class WeatherDetailsCashMapper : EntityMapper<HomeEntity, WeatherDetailsModel> {
    override fun mapFromEntity(entity: HomeEntity): WeatherDetailsModel {
        return WeatherDetailsModel(
            currentModel = entity.content.currentModel,
            alert = entity.content.alert,
            daily = entity.content.daily,
            hourly = entity.content.hourly,
            lat = entity.content.lat,
            lon = entity.content.lon,
            timezone = entity.content.timezone,
            timezone_offset = entity.content.timezone_offset
        )
    }

    override fun entityFromMap(domainModel: WeatherDetailsModel): HomeEntity {
        return HomeEntity(
            content = WeatherDetailsModel(
                currentModel = domainModel.currentModel,
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

    override fun mapListFromEntityList(entityList: List<HomeEntity>): List<WeatherDetailsModel> {
        return entityList.map { mapFromEntity(it) }
    }

    override fun entityListFromMapList(domainModelList: List<WeatherDetailsModel>): List<HomeEntity> {
        return domainModelList.map { entityFromMap(it) }
    }
}