package iti.android.wheatherappjetpackcompose.domainLayer.utils

interface EntityMapper<Entity, DomainModel> {
    fun mapFromEntity(entity: Entity): DomainModel
    fun entityFromMap(domainModel: DomainModel): Entity

    fun mapListFromEntityList(entityList: List<Entity>): List<DomainModel>
    fun entityListFromMapList(domainModelList: List<DomainModel>): List<Entity>
}