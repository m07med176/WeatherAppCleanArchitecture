package iti.android.wheatherappjetpackcompose.presentationLayer.ui.favorite

import MainCoroutineRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.gms.maps.model.LatLng
import iti.android.wheatherappjetpackcompose.dataLayer.repository.FakeRepository
import iti.android.wheatherappjetpackcompose.domainLayer.models.FavPlacesModel
import iti.android.wheatherappjetpackcompose.domainLayer.usecase.favorite.*
import iti.android.wheatherappjetpackcompose.domainLayer.utils.DataResponseState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
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
class FavoriteViewModelTest {


    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var favoriteViewModel: FavoriteViewModel

    lateinit var repository: FakeRepository

    @Before
    fun handler() {
        repository = FakeRepository(context = ApplicationProvider.getApplicationContext())

        favoriteViewModel = FavoriteViewModel(
            FavoriteUseCases(
                insertFavorite = InsertFavoriteUseCase(repository),
                deleteFavorite = DeleteFavoriteUseCase(repository),
                getSettingsUseCase = GetSettingsUseCase(repository),
                getFavoritesUseCase = GetFavoritesUseCase(repository)
            )
        )
    }


    @Test
    fun getCurrentLocation() = mainCoroutineRule.runBlockingTest {
        // Given

        // When
        val location = favoriteViewModel.getCurrentLocation().first()
        // Then

    }

    @Test
    fun getFavPlacesData() = mainCoroutineRule.runBlockingTest {
        // Given
        val excepectedSize = 3
        // When
        favoriteViewModel.getFavPlacesData()
        // Then
        val response = favoriteViewModel.state.first()
        when (response) {
            is DataResponseState.OnSuccess -> {
                assertThat(response.data.size, CoreMatchers.`is`(excepectedSize))
            }
            is DataResponseState.OnError -> {}
            is DataResponseState.OnNothingData -> {}
            is DataResponseState.OnLoading -> {}
        }

    }

    @Test
    fun insetFavoriteItem() = mainCoroutineRule.runBlockingTest {
        // Given
        val excepectedSize = 4
        val item = FavPlacesModel(
            city = "klhkj",
            location = LatLng(65.5, 5.56)
        )
        // When
        favoriteViewModel.insetFavoriteItem(item)

        // Then
        favoriteViewModel.getFavPlacesData()

        val response = favoriteViewModel.state.first()
        when (response) {
            is DataResponseState.OnSuccess -> {
                assertThat(response.data.size, CoreMatchers.`is`(excepectedSize))
            }
            is DataResponseState.OnError -> {}
            is DataResponseState.OnNothingData -> {}
            is DataResponseState.OnLoading -> {}
        }


    }

    @Test
    fun deleteFavoriteItem() = mainCoroutineRule.runBlockingTest {
        // Given
        val excepectedSize = 3
        val response = favoriteViewModel.state.first()
        when (response) {
            is DataResponseState.OnSuccess -> {
                // When
                favoriteViewModel.deleteFavoriteItem(response.data[0])
            }
            is DataResponseState.OnError -> {}
            is DataResponseState.OnNothingData -> {}
            is DataResponseState.OnLoading -> {}
        }


        // Then
        favoriteViewModel.getFavPlacesData()

        val responseTow = favoriteViewModel.state.first()
        when (responseTow) {
            is DataResponseState.OnSuccess -> {
                assertThat(responseTow.data.size, CoreMatchers.`is`(excepectedSize))
            }
            is DataResponseState.OnError -> {}
            is DataResponseState.OnNothingData -> {}
            is DataResponseState.OnLoading -> {}
        }


    }
}