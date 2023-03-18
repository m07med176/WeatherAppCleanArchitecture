package iti.android.wheatherappjetpackcompose.presentationLayer.ui.settings

import MainCoroutineRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import iti.android.wheatherappjetpackcompose.dataLayer.repository.FakeRepository
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.cash.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(sdk = [32])
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class SettingsViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var settingsViewModel: SettingsViewModel

    lateinit var repository: FakeRepository

    @Before
    fun handler() {
        repository = FakeRepository(context = ApplicationProvider.getApplicationContext())
        settingsViewModel = SettingsViewModel(repository)
    }

    @Test
    fun saveTemperature() {
        // Given : null value

        // When
        settingsViewModel.saveTemperature()

        // Then
        val temperature = repository.context.getSettingsTemperature()
        assertThat(temperature, CoreMatchers.`is`(Temperature.Celsius))
    }

    @Test
    fun saveWindSpeed() {
        // Given : null value

        // When
        settingsViewModel.saveWindSpeed()

        // Then
        val windSpeed = repository.context.getSettingsWindSpeed()
        assertThat(windSpeed, CoreMatchers.`is`(WindSpeed.Meter))
    }
}