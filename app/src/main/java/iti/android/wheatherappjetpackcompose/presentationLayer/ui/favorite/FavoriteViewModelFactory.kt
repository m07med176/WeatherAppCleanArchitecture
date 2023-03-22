package iti.android.wheatherappjetpackcompose.presentationLayer.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.favorite.FavoriteUseCases

class FavoriteViewModelFactory constructor(private val useCases: FavoriteUseCases) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            FavoriteViewModel(this.useCases) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
