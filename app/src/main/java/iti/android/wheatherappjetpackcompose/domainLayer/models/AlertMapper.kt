package iti.android.wheatherappjetpackcompose.domainLayer.models

import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.Alert
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.AlertEntity
import iti.android.wheatherappjetpackcompose.domainLayer.utils.EntityMapper

class AlertMapper : EntityMapper<AlertEntity, AlertModel> {
    override fun mapFromEntity(entity: AlertEntity): AlertModel {
        return AlertModel(
            id = entity.id,
            description = entity.content.description,
            end = entity.content.end,
            event = entity.content.event,
            senderName = entity.content.senderName,
            start = entity.content.start,
            tags = entity.content.tags
        )
    }

    override fun entityFromMap(domainModel: AlertModel): AlertEntity {
        return AlertEntity(
            id = domainModel.id,
            content = Alert(
                description = domainModel.description,
                end = domainModel.end,
                event = domainModel.event,
                senderName = domainModel.senderName,
                start = domainModel.start,
                tags = domainModel.tags
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