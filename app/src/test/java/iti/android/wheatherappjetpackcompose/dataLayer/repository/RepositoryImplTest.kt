package iti.android.wheatherappjetpackcompose.dataLayer.repository

import MainCoroutineRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import iti.android.wheatherappjetpackcompose.LocalFakeDataSource
import iti.android.wheatherappjetpackcompose.RemoteFakeDataSource
import iti.android.wheatherappjetpackcompose.common.Constants
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.*
import iti.android.wheatherappjetpackcompose.dataLayer.source.remote.retrofite.Units
import iti.android.wheatherappjetpackcompose.domainLayer.models.WeatherDetailsMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.Is
import org.hamcrest.core.IsNull
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(sdk = [32])
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class RepositoryImplTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    lateinit var repositoryImpl: RepositoryImpl
    lateinit var alertEntityList: MutableList<AlertEntity>
    lateinit var favoriteList: MutableList<FavoriteEntity>
    lateinit var homeCash: HomeEntity

    @Before
    fun prepareDependencies() {
        // Fake data source
        val alertEntity = AlertEntity(
            id = 1,
            endDate = 8562389652,
            startDate = 8562345623,
            startTime = 86535623,
            endTime = 854785,
            city = "dfs",
            longitude = 52.5856,
            latitude = 42.2445,
        )

        alertEntityList = mutableListOf(
            alertEntity, alertEntity, alertEntity
        )


        val favorite = FavoriteEntity(city = "Mohamed", latitude = 0.252, longitude = 0.532)
        favoriteList = mutableListOf(favorite, favorite, favorite)


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
        homeCash = HomeEntity(
            content = WeatherDetailsMapper().mapFromEntity(
                weatherSuccessResponse
            )
        )

        repositoryImpl = RepositoryImpl(
            localDataSource = LocalFakeDataSource(
                alertList = alertEntityList,
                favoriteList = favoriteList,
                homeCash = homeCash
            ),
            remoteDataSource = RemoteFakeDataSource(weatherSuccessResponse),
            context = ApplicationProvider.getApplicationContext()
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getAlerts_resultOfFavoriteListIsSameSize() = mainCoroutineRule.runBlockingTest {
        // Given
        // When
        val results = repositoryImpl.getAlerts().first()
        // Then
        assertThat(results.size, Is.`is`(1))
    }

    @Test
    fun insertAlert() = mainCoroutineRule.runBlockingTest {
        // Given
        val alertItem = alertEntityList[0]
        // When
        val id = repositoryImpl.insertAlert(alertItem)
        // Then
        assertThat(id, Is.`is`(3L))
    }

    @Test
    fun deleteAlertByObject() = mainCoroutineRule.runBlockingTest {
        // Given
        val alertItem = alertEntityList[0]
        // When
        repositoryImpl.deleteAlertByObject(alertItem)
        // Then
        assertThat(3, Is.`is`(alertEntityList.size))
    }

    @Test
    fun deleteAlert() = mainCoroutineRule.runBlockingTest {
        // Given
        val alertItem = alertEntityList[0]
        // When
        repositoryImpl.deleteAlert(alertItem.id?.toInt() ?: -1)
        // Then
        assertThat(3, Is.`is`(alertEntityList.size))
    }

    @Test
    fun getAlert() = mainCoroutineRule.runBlockingTest {
        // Given
        val alertItem = alertEntityList[0]
        // When
        val result = repositoryImpl.getAlert(alertItem.id?.toInt() ?: -1)
        // Then
        assertThat(result, IsNull.notNullValue())
    }


    @Test
    fun getHome() = mainCoroutineRule.runBlockingTest {
        // Given
        // When
        val result = repositoryImpl.getHome().first()
        // Then
        assertThat(result, IsNull.notNullValue())

    }

    @Test
    fun insertHome() = mainCoroutineRule.runBlockingTest {
        // Given
        // When
        repositoryImpl.insertHome(homeCash)
        // Then
        assertThat(homeCash, IsNull.nullValue())

    }

    @Test
    fun deleteHome() = mainCoroutineRule.runBlockingTest {
        // Given
        // When
        repositoryImpl.deleteHome(homeCash)
        // Then
        assertThat(homeCash, IsNull.nullValue())

    }

    @Test
    fun getWeatherDetails() = mainCoroutineRule.runBlockingTest {
        val result = repositoryImpl.getWeatherDetails(
            units = Units.imperial.name,
            longitude = 52.6,
            language = Constants.ENGLISH,
            latitude = 5.25
        )
        if (result.isSuccessful) {
            assertThat(result, IsNull.notNullValue())
        }
    }

    @Test
    fun getFavorites() = mainCoroutineRule.runBlockingTest {
        // Given
        // When
        val results = repositoryImpl.getFavorites().first()
        // Then
        assertThat(results.size, Is.`is`(3))
    }

    @Test
    fun insertFavorite() = mainCoroutineRule.runBlockingTest {
        // Given
        val item = favoriteList[0]
        // When
        repositoryImpl.insertFavorite(item)
        // Then
        assertThat(4, Is.`is`(alertEntityList.size))
    }

    @Test
    fun deleteFavorite() = mainCoroutineRule.runBlockingTest {
        // Given
        val item = favoriteList[0]
        // When
        repositoryImpl.deleteFavorite(item)
        // Then
        assertThat(2, Is.`is`(alertEntityList.size))
    }
}