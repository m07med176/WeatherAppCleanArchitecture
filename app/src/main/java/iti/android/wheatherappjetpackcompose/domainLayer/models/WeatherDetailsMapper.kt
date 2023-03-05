package iti.android.wheatherappjetpackcompose.domainLayer.models

import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.WeatherSuccessResponse
import iti.android.wheatherappjetpackcompose.domainLayer.utils.EntityMapper

class WeatherDetailsMapper : EntityMapper<WeatherSuccessResponse, WeatherDetailsModel> {
    override fun mapFromEntity(entity: WeatherSuccessResponse): WeatherDetailsModel {
        return WeatherDetailsModel(
            current = entity.current,
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
        return WeatherSuccessResponse(
            current = domainModel.current,
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