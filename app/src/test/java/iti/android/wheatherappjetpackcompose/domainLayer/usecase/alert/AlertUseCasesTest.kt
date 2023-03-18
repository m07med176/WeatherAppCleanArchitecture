package iti.android.wheatherappjetpackcompose.domainLayer.usecase.alert

import MainCoroutineRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import iti.android.wheatherappjetpackcompose.dataLayer.repository.FakeRepository
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.AlertEntity
import iti.android.wheatherappjetpackcompose.domainLayer.models.AlertMapper
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
class AlertUseCasesTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    lateinit var repository: FakeRepository
    lateinit var alertUseCases: AlertUseCases

    @Before
    fun handler() {
        // Fake data source
        repository = FakeRepository(context = ApplicationProvider.getApplicationContext())
        alertUseCases = AlertUseCases(
            getAlert = GetAlertUseCase(repository),
            insertAlert = InsertAlertsUseCase(repository),
            deleteAlert = DeleteAlertUseCase(repository)
        )

    }

    @Test
    fun getDeleteAlert() = mainCoroutineRule.runBlockingTest {
        // Given
        val alertModel = AlertMapper().mapFromEntity(repository.getAlerts().first()[0])
        // When
        alertUseCases.deleteAlert.invoke(alertModel)
        // Then
        val result = alertUseCases.getAlert.invoke().first()
        print(result)
        print(result.size)
        assertThat(result.size, `is`(2))
    }

    @Test
    fun getGetAlert() = mainCoroutineRule.runBlockingTest {
        // Given: Nothing
        val expectedSize = 3

        // When
        val result = alertUseCases.getAlert.invoke().first()
        print(result)
        print(result.size)
        // Then
        assertThat(result.size, `is`(expectedSize))
    }

    @Test
    fun getInsertAlert() = mainCoroutineRule.runBlockingTest {
        // Given
        val expectedId = 3L
        val item = AlertEntity()
        // When
        val resultId = alertUseCases.insertAlert.invoke(item)
        print(resultId)
        // Then
        assertThat(resultId, `is`(expectedId))
    }
}