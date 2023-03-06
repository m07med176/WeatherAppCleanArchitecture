package iti.android.wheatherappjetpackcompose.domainLayer.usecase.alert

import iti.android.wheatherappjetpackcompose.domainLayer.models.AlertMapper
import iti.android.wheatherappjetpackcompose.domainLayer.models.AlertModel
import iti.android.wheatherappjetpackcompose.domainLayer.repository.IAlertRepository

class DeleteAlertUseCase(private val repository: IAlertRepository) {
    suspend operator fun invoke(alertModel: AlertModel) {
        repository.deleteAlert(AlertMapper().entityFromMap(alertModel))
    }
}