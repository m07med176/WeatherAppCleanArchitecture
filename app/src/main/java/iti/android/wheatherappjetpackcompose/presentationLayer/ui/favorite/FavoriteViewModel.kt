package iti.android.wheatherappjetpackcompose.presentationLayer.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import iti.android.wheatherappjetpackcompose.domainLayer.models.FavPlacesModel
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.favorite.FavoriteUseCases
import iti.android.wheatherappjetpackcompose.domainLayer.utils.DataResponseState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavoriteViewModel(private val userCases: FavoriteUseCases) : ViewModel() {
    private val _state =
        MutableStateFlow<DataResponseState<List<FavPlacesModel>>>(DataResponseState.OnLoading())
    val state: StateFlow<DataResponseState<List<FavPlacesModel>>>
        get() = _state

    fun getCurrentLocation(): Flow<LatLng> = flow {
        emit(userCases.getSettingsUseCase.invoke().userLocation ?: LatLng(0.0, 0.0))
    }

    fun getFavPlacesData() {
        viewModelScope.launch {
            userCases.getFavoritesUseCase.invoke().catch {
                _state.value = DataResponseState.OnError(it.message.toString())
            }.collect { data ->
                if (data.isEmpty())
                    _state.value = DataResponseState.OnNothingData()
                else
                    _state.value = DataResponseState.OnSuccess(data)
            }
        }
    }

    fun insetFavoriteItem(favPlacesModel: FavPlacesModel) {
        viewModelScope.launch {
            userCases.insertFavorite.invoke(favPlacesModel)
        }
    }

    fun deleteFavoriteItem(favPlacesModel: FavPlacesModel) {
        viewModelScope.launch {
            userCases.deleteFavorite.invoke(favPlacesModel)
        }
    }
}