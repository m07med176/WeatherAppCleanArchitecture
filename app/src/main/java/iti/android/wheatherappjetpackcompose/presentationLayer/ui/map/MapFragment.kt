package iti.android.wheatherappjetpackcompose.presentationLayer.ui.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import iti.android.wheatherappjetpackcompose.BuildConfig
import iti.android.wheatherappjetpackcompose.R
import iti.android.wheatherappjetpackcompose.common.Constants
import iti.android.wheatherappjetpackcompose.dataLayer.repository.RepositoryImpl
import iti.android.wheatherappjetpackcompose.dataLayer.repository.RepositoryInterface
import iti.android.wheatherappjetpackcompose.domainLayer.models.FavPlacesModel
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.favorite.*
import iti.android.wheatherappjetpackcompose.presentationLayer.ui.favorite.FavoriteViewModel
import iti.android.wheatherappjetpackcompose.presentationLayer.ui.favorite.FavoriteViewModelFactory
import iti.android.wheatherappjetpackcompose.utils.Message
import iti.android.wheatherappjetpackcompose.utils.findNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


const val ZOOM_LEVEL = 15f
private const val TAG = "MapFragment"

class MapFragment : Fragment(), OnMapReadyCallback {

    private var chosenLocation: LatLng? = null
    private lateinit var view: View
    private lateinit var destination: MapDestination
    private lateinit var buttonSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var buttomSheet: ConstraintLayout
    private val viewModel: FavoriteViewModel by lazy {
        val repository: RepositoryInterface =
            RepositoryImpl.getInstance(requireActivity().application)
        val useCases = FavoriteUseCases(
            deleteFavorite = DeleteFavoriteUseCase(repository),
            insertFavorite = InsertFavoriteUseCase(repository),
            getFavoritesUseCase = GetFavoritesUseCase(repository),
            getSettingsUseCase = GetSettingsUseCase(repository)
        )
        ViewModelProvider(
            requireActivity(),
            FavoriteViewModelFactory(useCases)
        )[FavoriteViewModel::class.java]
    }

    private lateinit var mMap: GoogleMap

    private fun buttomSheetSettings() {
        buttomSheet = view.findViewById(R.id.bottomSheet)
        buttonSheetBehavior = BottomSheetBehavior.from(buttomSheet)
        buttonSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {}
                    BottomSheetBehavior.STATE_COLLAPSED -> {}
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        view = inflater.inflate(R.layout.fragment_map, container, false)
        destination = arguments?.getSerializable(Constants.MAP_DESTINATION) as MapDestination

        buttomSheetSettings()
        saveButtonHandler()
        return view
    }

    private fun saveButtonHandler() {
        val saveButton: Button = view.findViewById(R.id.saveLocationBtn)
        saveButton.setOnClickListener {
            when (destination) {
                MapDestination.FAVORITE -> {
                    chosenLocation?.let {
                        viewModel.insetFavoriteItem(FavPlacesModel(location = chosenLocation!!))
                        Message.snakeMessageCo(
                            requireContext(),
                            buttomSheet,
                            getString(R.string.save_favorite_message),
                            true
                        ).show()
                        lifecycleScope
                            .launch {
                                delay(500)
                                findNavController(requireActivity())?.popBackStack()
                            }
                    }
                }
                MapDestination.HOME -> {}
                MapDestination.SETTINGS -> {}
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map

        lifecycleScope.launch {
            viewModel.getCurrentLocation()?.collect { latLng ->
                mMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                )
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZOOM_LEVEL))
            }
        }
        setMapStyle(mMap, requireContext())
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

        onClickMapEvents()
    }

    private fun onClickMapEvents() {
        mMap.setOnMarkerClickListener {
            chosenLocation = it.position
            buttonSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            true
        }

        mMap.setOnMapLongClickListener { latLng ->
            mMap.addMarker(
                MarkerOptions()
                    .position(latLng)
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZOOM_LEVEL))
        }
        mMap.setOnMapClickListener { latLng ->
            buttonSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        mMap.setOnPoiClickListener {
            mMap.addMarker(
                MarkerOptions()
                    .title(it.name)
                    .position(it.latLng)
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it.latLng, ZOOM_LEVEL))
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.mMap) as SupportMapFragment
        mapFragment.getMapAsync(this)

        autoCompletePlacesAPI()
    }

    private fun autoCompletePlacesAPI() {
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
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it, ZOOM_LEVEL))

                }
            }

            override fun onError(status: Status) {
                Toast.makeText(requireContext(), status.toString(), Toast.LENGTH_SHORT).show()

            }
        })
    }


    fun setMapStyle(map: GoogleMap, context: Context) {
        try {
            val success = map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    context,
                    R.raw.map_style
                )
            )
            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", e)
        }
    }


}