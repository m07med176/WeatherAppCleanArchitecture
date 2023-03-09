package iti.android.wheatherappjetpackcompose.presentationLayer.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.settings.SettingsUseCases

class SettingsViewModelFactory constructor(private val useCases: SettingsUseCases) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            SettingsViewModel(this.useCases) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
