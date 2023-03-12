package iti.android.wheatherappjetpackcompose.domainLayer.usecase.alert

import iti.android.wheatherappjetpackcompose.dataLayer.repository.RepositoryInterface
import iti.android.wheatherappjetpackcompose.domainLayer.models.AlertMapper
import iti.android.wheatherappjetpackcompose.domainLayer.models.AlertModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAlertUseCase(private val repository: RepositoryInterface) {
    operator fun invoke(): Flow<List<AlertModel>> {
        return repository.getAlerts().map { AlertMapper().mapListFromEntityList(it) }
    }
}