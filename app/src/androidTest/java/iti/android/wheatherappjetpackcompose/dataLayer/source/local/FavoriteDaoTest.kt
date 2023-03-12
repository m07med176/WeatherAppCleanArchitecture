package iti.android.wheatherappjetpackcompose.dataLayer.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.FavoriteEntity
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.room.FavoriteDao
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.room.RoomDB
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class FavoriteDaoTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var favoriteTable: FavoriteDao
    private lateinit var database: RoomDB
    private lateinit var fakeData: List<FavoriteEntity>

    @Before
    fun initDB() {
        val favorite = FavoriteEntity(city = "Mohamed", latitude = 0.252, longitude = 0.532)
        fakeData = listOf(favorite, favorite, favorite)
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RoomDB::class.java
        ).allowMainThreadQueries().build()
        favoriteTable = database.favoriteDao()
    }

    @After
    fun closeDB() = database.close()


    @Test
    fun getFavorites() = runBlockingTest {
        // Gavin
        val item1 = fakeData.get(0)
        val item2 = fakeData.get(1)
        val item3 = fakeData.get(2)
        favoriteTable.insertFavorite(item1)
        favoriteTable.insertFavorite(item2)
        favoriteTable.insertFavorite(item3)
        // When
        val items = favoriteTable.getFavorites().first()
        // Then
        assertThat(items.size, Is.`is`(3))
    }


    @Test
    fun insertFavorite() = runBlockingTest {
        // Gavin
        val item = fakeData.get(0)
        // When
        favoriteTable.insertFavorite(item)
        // Then
        val result = favoriteTable.getFavorites().first().get(0)
        assertThat(result.latitude, Is.`is`(0.252))
    }

    @Test
    fun deleteFavorite() = runBlockingTest {
        // Gavin
        val item = fakeData.get(0)
        favoriteTable.insertFavorite(item)
        val items = favoriteTable.getFavorites().first()
        // When
        favoriteTable.deleteFavorite(items.get(0))
        // Then
        val result = favoriteTable.getFavorites().first()
        assertThat(result.size, Is.`is`(0))
    }
}