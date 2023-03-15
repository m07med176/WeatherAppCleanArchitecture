package iti.android.wheatherappjetpackcompose.presentationLayer.ui.settings

import androidx.annotation.IdRes
import androidx.lifecycle.ViewModel
import iti.android.wheatherappjetpackcompose.R
import iti.android.wheatherappjetpackcompose.dataLayer.repository.RepositoryInterface
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.cash.Temperature
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.cash.WindSpeed
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.cash.setSharedSettings

class SettingsViewModel(private val repository: RepositoryInterface) : ViewModel() {


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

        repository.context.setSharedSettings(temperature)
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
        repository.context.setSharedSettings(windSpeed)
    }


}