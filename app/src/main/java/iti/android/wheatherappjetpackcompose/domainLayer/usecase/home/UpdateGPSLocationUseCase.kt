package iti.android.wheatherappjetpackcompose.domainLayer.usecase.home

import com.google.android.gms.maps.model.LatLng
import iti.android.wheatherappjetpackcompose.dataLayer.repository.IMainRepository
import iti.android.wheatherappjetpackcompose.dataLayer.source.cash.LocationProvider

class UpdateGPSLocationUseCase(val repository: IMainRepository) {

    suspend operator fun invoke(latLng: LatLng) {
        repository.updateUserLocationSettings(latLng)
        repository.updateLocationProviderSettings(LocationProvider.GPS)
    }
}