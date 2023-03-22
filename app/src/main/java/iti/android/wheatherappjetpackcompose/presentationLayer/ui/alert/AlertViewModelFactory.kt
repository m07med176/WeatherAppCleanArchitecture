package iti.android.wheatherappjetpackcompose.presentationLayer.ui.alert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.alert.AlertUseCases

class AlertViewModelFactory constructor(private val useCases: AlertUseCases) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AlertViewModel::class.java)) {
            AlertViewModel(this.useCases) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
