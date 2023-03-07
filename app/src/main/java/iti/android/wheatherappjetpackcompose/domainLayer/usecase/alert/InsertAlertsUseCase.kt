package iti.android.wheatherappjetpackcompose.domainLayer.usecase.alert

import iti.android.wheatherappjetpackcompose.dataLayer.repository.IAlertRepository
import iti.android.wheatherappjetpackcompose.domainLayer.models.AlertMapper
import iti.android.wheatherappjetpackcompose.domainLayer.models.AlertModel

class InsertAlertsUseCase(private val repository: IAlertRepository) {
    suspend operator fun invoke(alertModel: AlertModel) {
        repository.insertAlert(AlertMapper().entityFromMap(alertModel))
    }
}