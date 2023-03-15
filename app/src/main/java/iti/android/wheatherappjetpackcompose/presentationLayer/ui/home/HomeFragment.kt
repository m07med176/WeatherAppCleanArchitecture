package iti.android.wheatherappjetpackcompose.presentationLayer.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
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
import iti.android.wheatherappjetpackcompose.presentationLayer.MainActivity
import iti.android.wheatherappjetpackcompose.presentationLayer.ui.map.MapDestination
import iti.android.wheatherappjetpackcompose.presentationLayer.utils.Message
import iti.android.wheatherappjetpackcompose.utils.GPSUtils
import iti.android.wheatherappjetpackcompose.utils.PermissionUtils.Companion.onRequestPermissionsResult
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapterHourly: HourlyAdapter
    private lateinit var adapterDaily: DailyAdapter

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



        binding.lifecycleOwner = this
        adapterHandler()

        binding.gpsBtn.setOnClickListener {
            getLocationByGPS()

        }

        binding.locationBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable(Constants.MAP_DESTINATION, MapDestination.HOME)
            NavHostFragment.findNavController(this).navigate(
                R.id.action_home_menu_to_mapFragment,
                bundle
            )
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

    private fun getWeatherData() {
        val bundle = arguments
        if (bundle != null) {
            val favoriteItem = bundle.getSerializable(Constants.FAV_ITEM) as FavPlacesModel
            viewModel.getWeatherData(favoriteItem.location)
        } else {
            viewModel.getWeatherData()
        }
    }

    private fun adapterHandler() {
        adapterHourly = HourlyAdapter()
        adapterDaily = DailyAdapter()
        binding.dailyAdapter = adapterDaily
        binding.hourlyAdapter = adapterHourly
    }

    override fun onResume() {
        super.onResume()
        getWeatherData()
    }

    private fun getLocationByGPS() {
        GPSUtils.getLastLocation(requireActivity()) { location ->
            viewModel.saveLocation(location)
            val intentActivity = Intent(requireContext(), MainActivity::class.java)
            startActivity(intentActivity)
        }
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


    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        GPSUtils.LOCATION_PERMISSIONS_LIST.onRequestPermissionsResult(requestCode,
            permissions,
            grantResults,
            { failedMesssage ->

            },
            { successMessage ->
                getLocationByGPS()

            })


    }
}