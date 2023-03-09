package iti.android.wheatherappjetpackcompose.presentationLayer.ui.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import iti.android.wheatherappjetpackcompose.dataLayer.source.cash.Settings
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.settings.SettingsUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(private val settingsUseCases: SettingsUseCases) : ViewModel() {

    private var _settings = MutableStateFlow(Settings())
    val settings: StateFlow<Settings>
        get() = _settings

    fun getSettingsData() {
        viewModelScope.launch {
            settingsUseCases.getSharedSettings.invoke().collect {
                _settings.value = it
            }
        }
    }


    fun saveTemperature(id: Int) {
        Log.d("TAGooo", "saveTemperature: $id")
    }
}