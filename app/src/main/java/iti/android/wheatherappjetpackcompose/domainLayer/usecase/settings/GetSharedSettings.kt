package iti.android.wheatherappjetpackcompose.domainLayer.usecase.settings

import iti.android.wheatherappjetpackcompose.dataLayer.repository.ISettingsRepository
import iti.android.wheatherappjetpackcompose.dataLayer.source.cash.Settings
import kotlinx.coroutines.flow.Flow

class GetSharedSettings(private var repository: ISettingsRepository) {
    operator fun invoke(): Flow<Settings> {
        return repository.getSharedSettings()
    }
}