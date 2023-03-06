package iti.android.wheatherappjetpackcompose.domainLayer.usecase.alert

import iti.android.wheatherappjetpackcompose.domainLayer.models.AlertMapper
import iti.android.wheatherappjetpackcompose.domainLayer.models.AlertModel
import iti.android.wheatherappjetpackcompose.domainLayer.repository.IAlertRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAlertUseCase(private val repository: IAlertRepository) {
    operator fun invoke(): Flow<List<AlertModel>> {
        return repository.getAlerts().map { AlertMapper().mapListFromEntityList(it) }
    }
}