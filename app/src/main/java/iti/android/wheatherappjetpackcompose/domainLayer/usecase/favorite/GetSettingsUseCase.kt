package iti.android.wheatherappjetpackcompose.domainLayer.usecase.favorite

import iti.android.wheatherappjetpackcompose.dataLayer.repository.RepositoryInterface
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.cash.Settings
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.cash.getSharedSettings

class GetSettingsUseCase(private val repository: RepositoryInterface) {

    operator fun invoke(): Settings {
        return repository.context.getSharedSettings()
    }
}