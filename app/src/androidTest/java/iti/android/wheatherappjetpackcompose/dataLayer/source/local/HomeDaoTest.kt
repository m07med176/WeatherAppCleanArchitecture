package iti.android.wheatherappjetpackcompose.dataLayer.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.Current
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.HomeEntity
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.WeatherSuccessResponse
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.room.HomeDao
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.room.RoomDB
import iti.android.wheatherappjetpackcompose.domainLayer.models.WeatherDetailsMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is
import org.hamcrest.core.IsNull
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class HomeDaoTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var homeTable: HomeDao
    private lateinit var database: RoomDB
    private lateinit var fakeData: List<WeatherSuccessResponse>

    @Before
    fun initDB() {
        val context = InstrumentationRegistry.getInstrumentation().context
//        var fakeData:List<WeatherSuccessResponse> = readRawJson(R.raw.fake_weather_data,context)

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
        fakeData = listOf(weatherSuccessResponse, weatherSuccessResponse, weatherSuccessResponse)
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RoomDB::class.java
        ).allowMainThreadQueries().build()
        homeTable = database.homeDao()
    }

    @After
    fun closeDB() = database.close()


    @Test
    fun getHome_insertItems_getCheckedItem() = runBlockingTest {
        // Gavin
        val item1 = HomeEntity(content = WeatherDetailsMapper().mapFromEntity(fakeData[0]))
        val item2 = HomeEntity(content = WeatherDetailsMapper().mapFromEntity(fakeData[1]))
        val item3 = HomeEntity(content = WeatherDetailsMapper().mapFromEntity(fakeData[2]))
        homeTable.insertHome(item1)
        homeTable.insertHome(item2)
        homeTable.insertHome(item3)
        // When
        val item = homeTable.getHome().first()
        // Then
        assertThat(item.content.lat, Is.`is`(0.256532))
    }


    @Test
    fun insertHome_insertItem_getInsertedItem() = runBlockingTest {
        // Gavin
        val item = HomeEntity(content = WeatherDetailsMapper().mapFromEntity(fakeData[0]))
        // When
        homeTable.insertHome(item)
        // Then
        val result = homeTable.getHome().first()
        assertThat(result.content.lat as Double, Is.`is`(0.256532))
    }

    @Test
    fun deleteHome_deleteItem_getNullValue() = runBlockingTest {
        // Gavin
        val item = HomeEntity(content = WeatherDetailsMapper().mapFromEntity(fakeData[0]))
        homeTable.insertHome(item)
        val insertedItem = homeTable.getHome().first()
        // When
        homeTable.deleteHome(insertedItem)
        // Then
        val result = homeTable.getHome().first()
        assertThat(result, IsNull.nullValue())
    }
}