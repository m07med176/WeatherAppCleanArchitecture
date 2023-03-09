package iti.android.wheatherappjetpackcompose.dataLayer.repository

import iti.android.wheatherappjetpackcompose.dataLayer.source.cash.DataStoreManager
import iti.android.wheatherappjetpackcompose.dataLayer.source.cash.Settings
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl(
    private val cash: DataStoreManager,
) : ISettingsRepository {

    override fun getSharedSettings(): Flow<Settings> {
        return cash.getSharedSettings()
    }


}