package iti.android.wheatherappjetpackcompose.domainLayer.usecase.alert

data class AlertUseCases(
    val deleteAlert: DeleteAlertUseCase,
    val getAlert: GetAlertUseCase,
    val insertAlert: InsertAlertsUseCase,
)
