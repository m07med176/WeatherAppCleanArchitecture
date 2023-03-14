package iti.android.wheatherappjetpackcompose.domainLayer.models

import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.AlertEntity
import iti.android.wheatherappjetpackcompose.domainLayer.utils.EntityMapper
import iti.android.wheatherappjetpackcompose.domainLayer.utils.TimeConverter

class AlertMapper : EntityMapper<AlertEntity, AlertModel> {
    override fun mapFromEntity(entity: AlertEntity): AlertModel {
        return AlertModel(
            endDate = TimeConverter.convertTimestampToString(
                entity.endDate ?: 0,
                TimeConverter.DATE_PATTERN
            ),
            endTime = TimeConverter.convertTimestampToString(
                entity.endTime ?: 0,
                TimeConverter.TIME_PATTERN
            ),
            startDate = TimeConverter.convertTimestampToString(
                entity.startDate ?: 0,
                TimeConverter.DATE_PATTERN
            ),
            startTime = TimeConverter.convertTimestampToString(
                entity.startTime ?: 0,
                TimeConverter.TIME_PATTERN
            )
        )
    }

    override fun entityFromMap(domainModel: AlertModel): AlertEntity {
        return AlertEntity(
            // Alert API
            id = domainModel.id,
            endDate = TimeConverter.convertStringToTimestamp(
                domainModel.endDate ?: "",
                TimeConverter.DATE_PATTERN
            ),
            endTime = TimeConverter.convertStringToTimestamp(
                domainModel.endTime ?: "",
                TimeConverter.TIME_PATTERN
            ),
            startDate = TimeConverter.convertStringToTimestamp(
                domainModel.startDate ?: "",
                TimeConverter.DATE_PATTERN
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