package iti.android.wheatherappjetpackcompose.domainLayer.models

import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.AlertEntity
import iti.android.wheatherappjetpackcompose.domainLayer.utils.EntityMapper
import iti.android.wheatherappjetpackcompose.domainLayer.utils.TimeConverter

class AlertMapper : EntityMapper<AlertEntity, AlertModel> {
    override fun mapFromEntity(entity: AlertEntity): AlertModel {
        return AlertModel(
            id=entity.id,
            latitude = entity.latitude ?: 0.0,
            longitude = entity.longitude ?: 0.0,
            city = entity.city,
            endDate = TimeConverter.convertTimestampToString(
                entity.endDate ?: 0,
                TimeConverter.DAY_PATTERN
            ),
            endTime = TimeConverter.convertTimestampToString(
                entity.endTime ?: 0,
                TimeConverter.TIME_PATTERN
            ),
            startDate = TimeConverter.convertTimestampToString(
                entity.startDate ?: 0,
                TimeConverter.DAY_PATTERN
            ),
            startTime = TimeConverter.convertTimestampToString(
                entity.startTime ?: 0,
                TimeConverter.TIME_PATTERN
            )
        )
    }

    override fun entityFromMap(domainModel: AlertModel): AlertEntity {
        return AlertEntity(
            id=domainModel.id,
            latitude = domainModel.latitude ?: 0.0,
            longitude = domainModel.longitude ?: 0.0,
            city = domainModel.city,
            endDate = TimeConverter.convertStringToTimestamp(
                domainModel.endDate ?: "",
                TimeConverter.DAY_PATTERN
            ),
            endTime = TimeConverter.convertStringToTimestamp(
                domainModel.endTime ?: "",
                TimeConverter.TIME_PATTERN
            ),
            startDate = TimeConverter.convertStringToTimestamp(
                domainModel.startDate ?: "",
                TimeConverter.DAY_PATTERN
            ),
            startTime = TimeConverter.convertStringToTimestamp(
                domainModel.startTime ?: "",
                TimeConverter.TIME_PATTERN
            )
        )
    }

    override fun mapListFromEntityList(entityList: List<AlertEntity>): List<AlertModel> {
        return entityList.map { mapFromEntity(it) }
    }

    override fun entityListFromMapList(domainModelList: List<AlertModel>): List<AlertEntity> {
        return domainModelList.map { entityFromMap(it) }
    }
}