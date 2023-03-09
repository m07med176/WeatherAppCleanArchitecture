package iti.android.wheatherappjetpackcompose.presentationLayer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.model.LatLng
import iti.android.wheatherappjetpackcompose.R
import iti.android.wheatherappjetpackcompose.dataLayer.repository.IMainRepository
import iti.android.wheatherappjetpackcompose.dataLayer.repository.MainRepositoryImpl
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.RoomDB
import iti.android.wheatherappjetpackcompose.dataLayer.source.remote.GeoCoderAPI
import iti.android.wheatherappjetpackcompose.dataLayer.source.remote.RetrofitInstance
import iti.android.wheatherappjetpackcompose.dataLayer.source.remote.Units
import iti.android.wheatherappjetpackcompose.databinding.FragmentHomeBinding
import iti.android.wheatherappjetpackcompose.domainLayer.models.WeatherDetailsModel
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.home.GetWeatherDetailsUseCase
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.home.HomeUseCases
import iti.android.wheatherappjetpackcompose.utils.DataResponseState
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapterHourly: HourlyAdapter
    private lateinit var adapterDaily: DailyAdapter

    private val viewModel: HomeViewModel by lazy {
        val db = RoomDB.invoke(requireContext()).homeDao()
        val retrofit = RetrofitInstance(requireContext())
        val geocoder = GeoCoderAPI(requireContext())
        val repository: IMainRepository = MainRepositoryImpl(
            dao = db,
            geoCoderAPI = geocoder,
            retrofitInstance = retrofit

        )
        val useCases = HomeUseCases(
            getWeatherDetailsUseCase = GetWeatherDetailsUseCase(repository)
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

        val latLng = LatLng(30.61554342119405, 32.27797547385768)
        viewModel.getWeatherData(latLng, Units.standard.name)
        binding.lifecycleOwner = this
        adapterHourly = HourlyAdapter()
        adapterDaily = DailyAdapter()
        binding.dailyAdapter = adapterDaily
        binding.hourlyAdapter = adapterHourly

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is DataResponseState.OnNothingData -> {}
                    is DataResponseState.OnError -> {}
                    is DataResponseState.OnSuccess -> {
                        dataVisible(true)
                        shimmerVisible(false)
                        updateData(state.data)

                    }
                    is DataResponseState.OnLoading -> {
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
}