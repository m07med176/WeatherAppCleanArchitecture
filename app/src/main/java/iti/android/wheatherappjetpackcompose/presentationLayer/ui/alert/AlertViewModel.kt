package iti.android.wheatherappjetpackcompose.presentationLayer.ui.alert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import iti.android.wheatherappjetpackcompose.domainLayer.models.AlertModel
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.alert.AlertUseCases
import iti.android.wheatherappjetpackcompose.utils.DataResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class AlertViewModel(private val useCases: AlertUseCases) : ViewModel() {
    private val _state =
        MutableStateFlow<DataResponseState<List<AlertModel>>>(DataResponseState.OnLoading())
    val state: StateFlow<DataResponseState<List<AlertModel>>>
        get() = _state


    fun getAlertsList() {
        viewModelScope.launch {
            useCases.getAlert.invoke().catch {
                _state.value = DataResponseState.OnError(it.message.toString())
            }.collect { items ->
                if (items.isEmpty())
                    _state.value = DataResponseState.OnNothingData()
                else
                    _state.value = DataResponseState.OnSuccess(items)
            }
        }
    }

}