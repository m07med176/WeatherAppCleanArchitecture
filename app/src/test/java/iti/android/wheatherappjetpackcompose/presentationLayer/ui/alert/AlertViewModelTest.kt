package iti.android.wheatherappjetpackcompose.presentationLayer.ui.alert

import MainCoroutineRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import iti.android.wheatherappjetpackcompose.dataLayer.repository.FakeRepository
import iti.android.wheatherappjetpackcompose.domainLayer.models.AlertMapper
import iti.android.wheatherappjetpackcompose.domainLayer.models.AlertModel
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.alert.AlertUseCases
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.alert.DeleteAlertUseCase
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.alert.GetAlertUseCase
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.alert.InsertAlertsUseCase
import iti.android.wheatherappjetpackcompose.domainLayer.utils.DataResponseState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(sdk = [32])
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class AlertViewModelTest {


    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var alertViewModel: AlertViewModel
    lateinit var repository: FakeRepository

    @Before
    fun handler() {
        repository = FakeRepository(context = ApplicationProvider.getApplicationContext())

        alertViewModel = AlertViewModel(
            AlertUseCases(
                getAlert = GetAlertUseCase(repository),
                deleteAlert = DeleteAlertUseCase(repository),
                insertAlert = InsertAlertsUseCase(repository)
            )
        )
    }

    @Test
    fun getState() {
        // Given
        // When
        // Then
    }

    @Test
    fun insertAlert() = mainCoroutineRule.runBlockingTest {
        // Given
        var excpectedId = 3L
        val item = AlertModel(
            city = "dfsf",
            latitude = 564.53,
            longitude = 565.5,
            endTime = "10:25 am",
            startTime = "10:25 am",
            startDate = "20 may",
            endDate = "20 may",
        )
        // When
        val result = alertViewModel.insertAlert(AlertMapper().entityFromMap(item))
        // Then
        assertThat(result, `is`(excpectedId))
    }

    @Test
    fun getAlertsList() = mainCoroutineRule.runBlockingTest {
        // Given
        val excepectedSize = 3
        // When
        alertViewModel.getAlertsList()
        // Then
        val response = alertViewModel.state.first()
        when (response) {
            is DataResponseState.OnSuccess -> {
                assertThat(response.data.size, `is`(excepectedSize))
            }
            is DataResponseState.OnError -> {}
            is DataResponseState.OnNothingData -> {}
            is DataResponseState.OnLoading -> {}
        }
    }

    @Test
    fun removeAlert() = mainCoroutineRule.runBlockingTest {
        // Given
        val excepectedSize = 2
        alertViewModel.getAlertsList()
        // When
        val response = alertViewModel.state.first()
        when (response) {
            is DataResponseState.OnSuccess -> {
                // When
                alertViewModel.removeAlert(response.data[0])
            }
            is DataResponseState.OnError -> {}
            is DataResponseState.OnNothingData -> {}
            is DataResponseState.OnLoading -> {}
        }

        // Then
        alertViewModel.getAlertsList()
        val results = alertViewModel.state.first()
        when (results) {
            is DataResponseState.OnSuccess -> {
                // When
                assertThat(results.data.size, `is`(excepectedSize))
            }
            is DataResponseState.OnError -> {}
            is DataResponseState.OnNothingData -> {}
            is DataResponseState.OnLoading -> {}
        }
    }
}