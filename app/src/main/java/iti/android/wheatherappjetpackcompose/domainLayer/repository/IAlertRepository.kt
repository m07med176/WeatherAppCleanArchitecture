package iti.android.wheatherappjetpackcompose.domainLayer.repository

import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.AlertEntity
import kotlinx.coroutines.flow.Flow

interface IAlertRepository {
    fun getAlerts(): Flow<List<AlertEntity>>
    suspend fun insertAlert(entity: AlertEntity)
    suspend fun deleteAlert(entity: AlertEntity)

}