package iti.android.wheatherappjetpackcompose.domainLayer.usecase.alert

import iti.android.wheatherappjetpackcompose.dataLayer.repository.RepositoryInterface
import iti.android.wheatherappjetpackcompose.domainLayer.models.AlertMapper
import iti.android.wheatherappjetpackcompose.domainLayer.models.AlertModel

class InsertAlertsUseCase(private val repository: RepositoryInterface) {
    suspend operator fun invoke(alertModel: AlertModel) {
        repository.insertAlert(AlertMapper().entityFromMap(alertModel))
    }
}