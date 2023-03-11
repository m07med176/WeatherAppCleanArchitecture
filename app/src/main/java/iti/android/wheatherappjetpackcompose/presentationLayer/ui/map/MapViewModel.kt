package iti.android.wheatherappjetpackcompose.presentationLayer.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import iti.android.wheatherappjetpackcompose.domainLayer.models.FavPlacesModel
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.map.MapUserCases
import kotlinx.coroutines.launch

class MapViewModel(private val mapUserCases: MapUserCases) : ViewModel() {
    fun insertFavorite(favPlacesModel: FavPlacesModel) {
        viewModelScope.launch {
            mapUserCases.insertFavorite.invoke(favPlacesModel)
        }
    }
}