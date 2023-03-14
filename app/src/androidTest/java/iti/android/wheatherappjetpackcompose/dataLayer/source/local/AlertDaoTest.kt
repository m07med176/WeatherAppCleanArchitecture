package iti.android.wheatherappjetpackcompose.dataLayer.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.Alert
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.AlertEntity
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.room.AlertDao
import iti.android.wheatherappjetpackcompose.dataLayer.source.local.room.RoomDB
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class AlertDaoTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var alertTable: AlertDao
    private lateinit var database: RoomDB
    private lateinit var fakeData: List<AlertEntity>

    @Before
    fun initDB() {

        val alertEntity = AlertEntity(
            content = Alert(
                description = "6jkdalskd",
                end = 56465,
                event = "dsfsaa",
                senderName = "kjldaskdjf",
                start = 564,
                tags = emptyList()
            )
        )

        fakeData = listOf(
            alertEntity, alertEntity, alertEntity
        )
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RoomDB::class.java
        ).allowMainThreadQueries().build()
        alertTable = database.alertDao()
    }

    @After
    fun closeDB() = database.close()

    @Test
    fun getAlerts() = runBlockingTest {
        // Gavin
        val item1 = fakeData[0]
        val item2 = fakeData[1]
        val item3 = fakeData[2]
        alertTable.insertAlert(item1)
        alertTable.insertAlert(item2)
        alertTable.insertAlert(item3)
        // When
        val items = alertTable.getAlerts().first()
        // Then
        MatcherAssert.assertThat(items.size, Is.`is`(3))
    }

    @Test
    fun insertAlert() = runBlockingTest {
        // Gavin
        val item = fakeData.get(0)
        // When
        alertTable.insertAlert(item)
        // Then
        val result = alertTable.getAlerts().first().get(0)
        MatcherAssert.assertThat(result.content.end, Is.`is`(56465))
    }

    @Test
    fun deleteAlert() = runBlockingTest {
        // Gavin
        val item = fakeData[0]
        alertTable.insertAlert(item)
        val items = alertTable.getAlerts().first()
        // When
        alertTable.deleteAlertByObject(items[0])
        // Then
        val result = alertTable.getAlerts().first()
        MatcherAssert.assertThat(result.size, Is.`is`(0))
    }
}