package iti.android.wheatherappjetpackcompose.dataLayer.repository

import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.AlertEntity
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.AlertDao
import kotlinx.coroutines.flow.Flow

class AlertRepositoryImpl(private val dao: AlertDao) : IAlertRepository {
    override fun getAlerts(): Flow<List<AlertEntity>> {
        return dao.getAlerts()
    }

    override suspend fun insertAlert(entity: AlertEntity) {
        dao.insertAlert(entity)
    }

    override suspend fun deleteAlert(entity: AlertEntity) {
        dao.deleteAlert(entity)
    }
}