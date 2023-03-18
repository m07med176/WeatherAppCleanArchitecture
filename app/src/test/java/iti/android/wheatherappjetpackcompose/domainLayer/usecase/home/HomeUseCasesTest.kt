package iti.android.wheatherappjetpackcompose.domainLayer.usecase.home

import MainCoroutineRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.gms.maps.model.LatLng
import iti.android.wheatherappjetpackcompose.dataLayer.repository.FakeRepository
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.*
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.cash.LocationProvider
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.cash.getSettingsLocationProvider
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.cash.getSharedSettings
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(sdk = [32])
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class HomeUseCasesTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    lateinit var repository: FakeRepository

    lateinit var homeUseCases: HomeUseCases

    @Before
    fun handler() {
        // Fake data source
        repository = FakeRepository(context = ApplicationProvider.getApplicationContext())
        homeUseCases = HomeUseCases(
            getWeatherDetailsUseCase = GetWeatherDetailsUseCase(repository),
            updateGPSLocation = UpdateGPSLocationUseCase(repository),
        )

    }

    @Test
    fun getGetWeatherDetailsUseCase_inputLocation() = mainCoroutineRule.runBlockingTest {
        // Given
        val latLng = LatLng(25.0, 56.05)
        // When
        val result = homeUseCases.getWeatherDetailsUseCase.invoke(latLng).first()
        // Then
        when (result) {
            is HomeResponseState.OnSuccess -> {
                assertThat(result.data, notNullValue())
            }
            is HomeResponseState.OnError -> {}
            is HomeResponseState.OnNoLocationDetected -> {}
            is HomeResponseState.OnLoading -> {}
            is HomeResponseState.OnNothingData -> {}
        }
    }

    @Test
    fun getGetWeatherDetailsUseCase_nullLocation() = mainCoroutineRule.runBlockingTest {
        // Given: Nothing just Null
        // When
        val result = homeUseCases.getWeatherDetailsUseCase.invoke().first()
        // Then
        when (result) {
            is HomeResponseState.OnSuccess -> {
                assertThat(result.data, notNullValue())
            }
            is HomeResponseState.OnError -> {}
            is HomeResponseState.OnNoLocationDetected -> {}
            is HomeResponseState.OnLoading -> {}
            is HomeResponseState.OnNothingData -> {}
        }

    }

    @Test
    fun getUpdateGPSLocation() {
        // Given
        val latLng = LatLng(25.0, 56.05)
        // When
        homeUseCases.updateGPSLocation.invoke(latLng)

        // Then

        //  setSharedSettings(latLng)
        val settings = repository.context.getSharedSettings()
        assertThat(settings.userLocation, `is`(latLng))
        //  setSharedSettings(LocationProvider.GPS)
        val locationProvider = repository.context.getSettingsLocationProvider()
        assertThat(locationProvider, `is`(LocationProvider.GPS))
    }
}