package iti.android.wheatherappjetpackcompose.presentationLayer.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import iti.android.wheatherappjetpackcompose.R
import iti.android.wheatherappjetpackcompose.common.Constants
import iti.android.wheatherappjetpackcompose.dataLayer.repository.RepositoryImpl
import iti.android.wheatherappjetpackcompose.dataLayer.repository.RepositoryInterface
import iti.android.wheatherappjetpackcompose.databinding.FragmentHomeBinding
import iti.android.wheatherappjetpackcompose.domainLayer.models.FavPlacesModel
import iti.android.wheatherappjetpackcompose.domainLayer.models.WeatherDetailsModel
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.home.GetWeatherDetailsUseCase
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.home.HomeResponseState
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.home.HomeUseCases
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.home.UpdateGPSLocationUseCase
import iti.android.wheatherappjetpackcompose.utils.Message
import iti.android.wheatherappjetpackcompose.utils.findNavController
import kotlinx.coroutines.launch

const val PERMISSION_REQUEST = 565

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapterHourly: HourlyAdapter
    private lateinit var adapterDaily: DailyAdapter
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    // TODO refactoring GPS location

    private val viewModel: HomeViewModel by lazy {
        val repository: RepositoryInterface =
            RepositoryImpl.getInstance(requireActivity().application)

        val useCases = HomeUseCases(
            getWeatherDetailsUseCase = GetWeatherDetailsUseCase(repository),
            updateGPSLocation = UpdateGPSLocationUseCase(repository)
        )
        ViewModelProvider(
            requireActivity(),
            HomeViewModelFactory(useCases)
        )[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        val bundle = arguments
        if (bundle != null) {
            val favoriteItem = bundle.getSerializable(Constants.FAV_ITEM) as FavPlacesModel
            viewModel.getWeatherData(favoriteItem.location)
        } else {
            viewModel.getWeatherData()

        }

        binding.lifecycleOwner = this
        adapterHourly = HourlyAdapter()
        adapterDaily = DailyAdapter()
        binding.dailyAdapter = adapterDaily
        binding.hourlyAdapter = adapterHourly

        binding.gpsBtn.setOnClickListener {
            if (checkPermission()) {
                if (isEnapledLocation()) {
                    Toast.makeText(requireContext(), "Permission Granted", Toast.LENGTH_SHORT)
                        .show()
                    getLastLocation()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "You should enable location",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                requestPermission()
            }
        }

        binding.locationBtn.setOnClickListener {
            findNavController(requireActivity())?.navigate(R.id.action_home_menu_to_mapFragment)
        }

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is HomeResponseState.OnNoLocationDetected -> {
                        binding.requestLocationStateHolder.visibility = View.VISIBLE
                        binding.errorStateHolder.visibility = View.GONE
                        binding.dataStateHolder.visibility = View.GONE
                    }
                    is HomeResponseState.OnNothingData -> {}
                    is HomeResponseState.OnError -> {
                        binding.errorStateHolder.visibility = View.VISIBLE
                        binding.dataStateHolder.visibility = View.GONE
                        binding.requestLocationStateHolder.visibility = View.GONE
                        Message.snakeMessage(
                            requireContext(),
                            binding.homePageFrame,
                            state.message,
                            false
                        ).show()
                    }
                    is HomeResponseState.OnSuccess -> {
                        dataVisible(true)
                        shimmerVisible(false)
                        updateData(state.data)

                    }
                    is HomeResponseState.OnLoading -> {
                        dataVisible(false)
                        shimmerVisible(true)
                    }
                }
            }
        }
        return binding.root
    }

    private fun updateData(data: WeatherDetailsModel) {
        adapterHourly.submitList(data.hourly)
        adapterDaily.submitList(data.daily)
        binding.dataModel = data
    }

    private fun dataVisible(setVisible: Boolean = true) {
        binding.recyclerViewDaily.visibility = if (setVisible) View.VISIBLE else View.GONE
        binding.recyclerViewHourly.visibility = if (setVisible) View.VISIBLE else View.GONE
        binding.measurementsHolder.visibility = if (setVisible) View.VISIBLE else View.GONE
        binding.currentHolder.visibility = if (setVisible) View.VISIBLE else View.GONE
    }

    private fun shimmerVisible(setVisible: Boolean = true) {
        binding.shimmerDaily.visibility = if (setVisible) View.VISIBLE else View.GONE
        binding.shimmerHourly.visibility = if (setVisible) View.VISIBLE else View.GONE
        binding.shimmerCurrent.visibility = if (setVisible) View.VISIBLE else View.GONE
        binding.shimmerMeasurements.visibility = if (setVisible) View.VISIBLE else View.GONE
    }


    @SuppressLint("MissingPermission")
    fun getLastLocation() {
        fusedLocationProviderClient
            .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location ->
                viewModel.saveLocation(location)
            }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(), listOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
            ).toTypedArray(), PERMISSION_REQUEST
        )
    }

    private fun checkPermission(): Boolean {
        val fineLoation = ActivityCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val coarseLocation = ActivityCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        return fineLoation && coarseLocation

    }

    private fun isEnapledLocation(): Boolean {
        val locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return LocationManagerCompat.isLocationEnabled(locationManager)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            }
        }

    }
}