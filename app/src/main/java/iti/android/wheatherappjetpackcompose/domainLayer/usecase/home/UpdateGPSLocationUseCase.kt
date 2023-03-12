package iti.android.wheatherappjetpackcompose.domainLayer.usecase.home

import com.google.android.gms.maps.model.LatLng
import iti.android.wheatherappjetpackcompose.dataLayer.repository.RepositoryInterface
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.cash.LocationProvider

class UpdateGPSLocationUseCase(val repository: RepositoryInterface) {

    suspend operator fun invoke(latLng: LatLng) {
        repository.updateUserLocationSettings(latLng)
        repository.updateLocationProviderSettings(LocationProvider.GPS)
    }
}