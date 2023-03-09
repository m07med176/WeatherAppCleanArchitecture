package iti.android.wheatherappjetpackcompose.presentationLayer.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import iti.android.wheatherappjetpackcompose.dataLayer.repository.ISettingsRepository

class SettingsViewModelFactory constructor(private val repository: ISettingsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            SettingsViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
