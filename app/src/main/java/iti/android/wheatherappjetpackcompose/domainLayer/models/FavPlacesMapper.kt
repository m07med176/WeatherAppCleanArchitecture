package iti.android.wheatherappjetpackcompose.domainLayer.models

import com.google.android.gms.maps.model.LatLng
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.FavoriteEntity
import iti.android.wheatherappjetpackcompose.domainLayer.utils.EntityMapper

class FavPlacesMapper : EntityMapper<FavoriteEntity, FavPlacesModel> {
    override fun mapFromEntity(entity: FavoriteEntity): FavPlacesModel {
        return FavPlacesModel(
            id = entity.id,
            city = entity.city,
            location = LatLng(entity.latitude, entity.longitude)
        )
    }

    override fun entityFromMap(domainModel: FavPlacesModel): FavoriteEntity {
        return FavoriteEntity(
            id = domainModel.id,
            city = domainModel.city,
            latitude = domainModel.location.latitude,
            longitude = domainModel.location.longitude,
        )
    }

    override fun mapListFromEntityList(entityList: List<FavoriteEntity>): List<FavPlacesModel> {
        return entityList.map { mapFromEntity(it) }
    }

    override fun entityListFromMapList(domainModelList: List<FavPlacesModel>): List<FavoriteEntity> {
        return domainModelList.map { entityFromMap(it) }
    }
}