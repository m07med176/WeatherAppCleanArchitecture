package iti.android.wheatherappjetpackcompose.presentationLayer.ui.home

import MainCoroutineRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import iti.android.wheatherappjetpackcompose.dataLayer.repository.FakeRepository
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.Current
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.HomeEntity
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.WeatherSuccessResponse
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.cash.LocationProvider
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.cash.getSettingsLocationProvider
import iti.android.wheatherappjetpackcompose.domainLayer.models.WeatherDetailsMapper
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.home.GetWeatherDetailsUseCase
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.home.HomeResponseState
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.home.HomeUseCases
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.home.UpdateGPSLocationUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(sdk = [32])
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class HomeViewModelTest {

    // Fake Data
    val weatherSuccessResponse = WeatherSuccessResponse(
        lat = 0.256532,
        lon = 0.065535,
        timezone = "56352232",
        timezone_offset = 55,
        current = Current(
            clouds = 54.toDouble(),
            dew_point = 0.5455,
            dt = 51.toDouble(),
            feels_like = 5.24,
            humidity = 5635.toDouble(),
            pressure = 545.toDouble(),
            sunrise = 54645.toDouble(),
            sunset = 564.toDouble(),
            temp = 0.224,
            uvi = 0.2454,
            visibility = 45.toDouble(),
            weather = emptyList(),
            wind_deg = 554.toDouble(),
            wind_gust = 0.25,
            wind_speed = 564.toDouble()
        )

    )
    val homeCash = HomeEntity(
        content = WeatherDetailsMapper().mapFromEntity(
            weatherSuccessResponse
        )
    )

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var homeViewModel: HomeViewModel

    lateinit var repository: FakeRepository

    @Before
    fun handler() {
        repository = FakeRepository(context = ApplicationProvider.getApplicationContext())

        homeViewModel =
            HomeViewModel(
                HomeUseCases(
                    updateGPSLocation = UpdateGPSLocationUseCase(repository),
                    getWeatherDetailsUseCase = GetWeatherDetailsUseCase(repository)
                )
            )
    }


    @Test
    fun getWeatherData() = mainCoroutineRule.runBlockingTest {
        // Given: Nothing
        // When
        homeViewModel.getWeatherData()
        // Then
        val response = homeViewModel.state.first()
        when (response) {
            is HomeResponseState.OnNothingData -> {}
            is HomeResponseState.OnError -> {}
            is HomeResponseState.OnLoading -> {}
            is HomeResponseState.OnSuccess -> {
                Assert.assertThat(response.data, notNullValue())

            }
            is HomeResponseState.OnNoLocationDetected -> {}
        }

    }

    @Test
    fun saveLocation() {
        // Given : null value

        // When
        homeViewModel.saveLocation(null)

        // Then
        val locationProvider = repository.context.getSettingsLocationProvider()
        Assert.assertThat(locationProvider, CoreMatchers.`is`(LocationProvider.GPS))
    }
}