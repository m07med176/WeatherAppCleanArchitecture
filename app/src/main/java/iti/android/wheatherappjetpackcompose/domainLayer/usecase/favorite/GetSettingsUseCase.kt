package iti.android.wheatherappjetpackcompose.domainLayer.usecase.favorite

import iti.android.wheatherappjetpackcompose.dataLayer.repository.RepositoryInterface
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.cash.Settings
import kotlinx.coroutines.flow.Flow

class GetSettingsUseCase(private val repository: RepositoryInterface) {

    operator fun invoke(): Flow<Settings> {
        return repository.getSharedSettings()
    }
}