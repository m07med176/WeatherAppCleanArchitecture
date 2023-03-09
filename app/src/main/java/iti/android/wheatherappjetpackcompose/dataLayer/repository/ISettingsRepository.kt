package iti.android.wheatherappjetpackcompose.dataLayer.repository


import iti.android.wheatherappjetpackcompose.dataLayer.source.cash.Settings
import kotlinx.coroutines.flow.Flow

interface ISettingsRepository {
    fun getSharedSettings(): Flow<Settings>
}