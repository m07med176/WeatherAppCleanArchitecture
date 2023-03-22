package iti.android.wheatherappjetpackcompose.domainLayer.usecase.home

import com.google.android.gms.maps.model.LatLng
import iti.android.wheatherappjetpackcompose.dataLayer.repository.RepositoryInterface
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.cash.LocationProvider
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.cash.setSharedSettings

class UpdateGPSLocationUseCase(val repository: RepositoryInterface) {

    operator fun invoke(latLng: LatLng) {
        repository.context.setSharedSettings(latLng)
        repository.context.setSharedSettings(LocationProvider.GPS)
    }
}