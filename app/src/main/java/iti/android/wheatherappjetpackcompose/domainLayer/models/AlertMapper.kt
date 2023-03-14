package iti.android.wheatherappjetpackcompose.domainLayer.models

import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.AlertEntity
import iti.android.wheatherappjetpackcompose.domainLayer.utils.EntityMapper

class AlertMapper : EntityMapper<AlertEntity, AlertModel> {
    override fun mapFromEntity(entity: AlertEntity): AlertModel {
        return AlertModel(
            latitude = entity.latitude ?: 0.0,
            longitude = entity.longitude ?: 0.0,
            city = entity.city,
            endDate = entity.endDate ?: 0,
            endTime = entity.endTime ?: 0,
            startDate = entity.startDate ?: 0,
            startTime = entity.startTime ?: 0
        )
    }

    override fun entityFromMap(domainModel: AlertModel): AlertEntity {
        return AlertEntity(
            latitude = domainModel.latitude ?: 0.0,
            longitude = domainModel.longitude ?: 0.0,
            city = domainModel.city,
            endDate = domainModel.endDate ?: 0,
            endTime = domainModel.endTime ?: 0,
            startDate = domainModel.startDate ?: 0,
            startTime = domainModel.startTime ?: 0
        )
    }

    override fun mapListFromEntityList(entityList: List<AlertEntity>): List<AlertModel> {
        return entityList.map { mapFromEntity(it) }
    }

    override fun entityListFromMapList(domainModelList: List<AlertModel>): List<AlertEntity> {
        return domainModelList.map { entityFromMap(it) }
    }
}