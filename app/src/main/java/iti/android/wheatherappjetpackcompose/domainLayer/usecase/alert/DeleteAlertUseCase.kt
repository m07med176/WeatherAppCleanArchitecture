package iti.android.wheatherappjetpackcompose.domainLayer.usecase.alert

import iti.android.wheatherappjetpackcompose.dataLayer.repository.RepositoryInterface
import iti.android.wheatherappjetpackcompose.domainLayer.models.AlertMapper
import iti.android.wheatherappjetpackcompose.domainLayer.models.AlertModel

class DeleteAlertUseCase(private val repository: RepositoryInterface) {
    suspend operator fun invoke(alertModel: AlertModel) {
        repository.deleteAlertByObject(AlertMapper().entityFromMap(alertModel))
    }
}