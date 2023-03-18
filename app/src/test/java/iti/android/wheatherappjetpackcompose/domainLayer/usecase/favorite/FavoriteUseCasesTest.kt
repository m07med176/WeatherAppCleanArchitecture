package iti.android.wheatherappjetpackcompose.domainLayer.usecase.favorite

import MainCoroutineRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.gms.maps.model.LatLng
import iti.android.wheatherappjetpackcompose.dataLayer.repository.FakeRepository
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.cash.Language
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.cash.Temperature
import iti.android.wheatherappjetpackcompose.domainLayer.models.FavPlacesMapper
import iti.android.wheatherappjetpackcompose.domainLayer.models.FavPlacesModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.Is
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(sdk = [32])
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class FavoriteUseCasesTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    lateinit var favoriteUseCases: FavoriteUseCases
    lateinit var repository: FakeRepository

    @Before
    fun handler() {
        // Fake data source
        repository = FakeRepository(context = ApplicationProvider.getApplicationContext())
        favoriteUseCases = FavoriteUseCases(
            getFavoritesUseCase = GetFavoritesUseCase(repository),
            insertFavorite = InsertFavoriteUseCase(repository),
            deleteFavorite = DeleteFavoriteUseCase(repository),
            getSettingsUseCase = GetSettingsUseCase(repository)
        )

    }

    @Test
    fun getDeleteFavorite() = mainCoroutineRule.runBlockingTest {
        // Given
        val expectedSize = 2
        val item = FavPlacesMapper().mapFromEntity(repository.getFavorites().first()[1])
        // When
        favoriteUseCases.deleteFavorite.invoke(item)
        // Then
        val results = favoriteUseCases.getFavoritesUseCase.invoke().first()
        print(results)
        print(results.size)
        // Then
        assertThat(results.size, Is.`is`(expectedSize))
    }

    @Test
    fun getInsertFavorite() = mainCoroutineRule.runBlockingTest {
        // Given
        val expectedSize = 4
        val favoritePlacesModel = FavPlacesModel(city = "asd", location = LatLng(565.85, 65.5))
        // When
        favoriteUseCases.insertFavorite.invoke(favoritePlacesModel)
        // Then
        val result = favoriteUseCases.getFavoritesUseCase.invoke().first()
        print(result)
        print(result.size)
        // Then
        assertThat(result.size, Is.`is`(expectedSize))
    }

    @Test
    fun getGetFavoritesUseCase() = mainCoroutineRule.runBlockingTest {
        // Given: Nothing
        val expectedSize = 3
        // When
        val results = favoriteUseCases.getFavoritesUseCase.invoke().first()
        print(results)
        print(results.size)
        // Then
        assertThat(results.size, Is.`is`(expectedSize))
    }

    @Test
    fun getGetSettingsUseCase() = mainCoroutineRule.runBlockingTest {
        // Given: Nothing
        val expectedTemperature = Temperature.Celsius
        val expectedLanguage = Language.English
        // When
        val results = favoriteUseCases.getSettingsUseCase.invoke()
        // Then
        assertThat(results.temperature, Is.`is`(expectedTemperature))
        assertThat(results.language, Is.`is`(expectedLanguage))
    }
}