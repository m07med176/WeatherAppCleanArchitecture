package iti.android.wheatherappjetpackcompose.domainLayer.usecase.alert

import iti.android.wheatherappjetpackcompose.dataLayer.repository.RepositoryInterface
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.AlertEntity

class InsertAlertsUseCase(private val repository: RepositoryInterface) {
    suspend operator fun invoke(alertModel: AlertEntity): Long {
        return repository.insertAlert(alertModel)
    }
}