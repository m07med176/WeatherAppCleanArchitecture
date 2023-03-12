package iti.android.wheatherappjetpackcompose.presentationLayer.ui.settings

import androidx.annotation.IdRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import iti.android.wheatherappjetpackcompose.R
import iti.android.wheatherappjetpackcompose.dataLayer.repository.RepositoryInterface
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.cash.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(private val repository: RepositoryInterface) : ViewModel() {

    private var _settings = MutableStateFlow(Settings())
    val settings: StateFlow<Settings>
        get() = _settings

    fun getSettingsData() {
        viewModelScope.launch {
            repository.getSharedSettings().collect {
                _settings.value = it
            }
        }
    }


    fun saveTemperature(@IdRes id: Int) {
        var temperature: Temperature = Temperature.Celsius
        when (id) {
            R.id.kelvinSelectedRadio -> {
                temperature = Temperature.Kelvin
            }
            R.id.celsiusSelectedRadio -> {
                temperature = Temperature.Celsius
            }
            R.id.fahrenhiteSelectedRadio -> {
                temperature = Temperature.Fahrenheit
            }
        }

        viewModelScope.launch { repository.updateTempraturSettings(temperature) }
    }

    fun saveWindSpeed(@IdRes id: Int) {
        var windSpeed = WindSpeed.Meter
        when (id) {
            R.id.milesSelectedRadio -> {
                windSpeed = WindSpeed.Miles
            }
            R.id.meterSelectedRadio -> {
                windSpeed = WindSpeed.Meter
            }
        }
        viewModelScope.launch { repository.updateWindSpeedSettings(windSpeed) }


    }

    fun saveLocationProvider(@IdRes id: Int) {
        var locationProvider = LocationProvider.Nothing
        when (id) {
            R.id.gpsSelectedRadio -> {
                locationProvider = LocationProvider.GPS
            }
            R.id.mapSelectedRadio -> {
                locationProvider = LocationProvider.MAP
            }
        }

        viewModelScope.launch { repository.updateLocationProviderSettings(locationProvider) }

    }

    fun saveLanguage(@IdRes id: Int) {
        var language = Language.English
        when (id) {
            R.id.arabicSelectedRadio -> {
                language = Language.Arabic
            }
            R.id.englishSelectedRadio -> {
                language = Language.English
            }
        }
        viewModelScope.launch { repository.updateLanguageSettings(language) }

    }
}