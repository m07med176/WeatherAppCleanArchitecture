package iti.android.wheatherappjetpackcompose.presentationLayer.ui.map

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import iti.android.wheatherappjetpackcompose.BuildConfig
import iti.android.wheatherappjetpackcompose.R

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {


        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map
        if (!(ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
                    )
        ) {
            mMap.isMyLocationEnabled = true
        }
        mMap.setOnMarkerClickListener {
            checkSaveToFavorite()
            true
        }

        mMap.setOnMapClickListener { latLng ->
            mMap.addMarker(
                MarkerOptions()
                    .position(latLng)
//                    .title(place.name))
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
        }
    }


    fun checkSaveToFavorite() {
        val alert: AlertDialog.Builder = AlertDialog.Builder(requireActivity())

        alert.setTitle("Favorite")

        alert.setMessage("Do You want to save this place on favorite")
        alert.setPositiveButton("Save") { _: DialogInterface, _: Int ->
            Toast.makeText(requireContext(), "Data has been saved", Toast.LENGTH_SHORT).show()

        }
        alert.setNegativeButton("No") { _: DialogInterface, _: Int ->
//              dialog.dismiss()
        }
        val dialog = alert.create()
        dialog.show()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.mMap) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val apiKey = BuildConfig.MAPS_API_KEY
        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), apiKey)
        }

        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.place_autocomplete_fragment) as AutocompleteSupportFragment
        autocompleteFragment.setTypeFilter(TypeFilter.CITIES)
        autocompleteFragment.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.PHOTO_METADATAS,
                Place.Field.LAT_LNG,
                Place.Field.ADDRESS
            )
        )

        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                place.latLng?.let {
                    mMap.addMarker(
                        MarkerOptions()
                            .position(it)
                            .title(place.name)
                    )
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it, 10f))

                }

//                Toast.makeText(
//                   requireContext(),
//                    place.name,
//                    Toast.LENGTH_SHORT
//                ).show()
//
//                val photoRequest = FetchPhotoRequest.builder(
//                    Objects.requireNonNull(place.photoMetadatas)[0]
//                )
//                    .build()
//                placesClient.fetchPhoto(photoRequest).addOnSuccessListener { response ->
//                    val bitmap = response.bitmap
////                    (findViewById(R.id.img) as ImageView).setImageBitmap(bitmap)
//                }
//                    .addOnFailureListener { exception -> exception.printStackTrace() }
            }

            override fun onError(status: Status) {
                Toast.makeText(requireContext(), status.toString(), Toast.LENGTH_SHORT).show()

            }
        })
    }

}