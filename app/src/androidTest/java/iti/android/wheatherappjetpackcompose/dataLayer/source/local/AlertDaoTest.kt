import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
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
            endDate = 8562389652,
            startDate = 8562345623,
            startTime = 86535623,
            endTime = 854785,
            city = "dfs",
            longitude = 52.5856,
            latitude = 42.2445,
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
    fun getAlerts_insertItems_getSameSize() = runBlockingTest {
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
    fun insertAlert_insertItem_getSameItem() = runBlockingTest {
        // Gavin
        val item = fakeData[0]
        // When
        alertTable.insertAlert(item)
        // Then
        val result = alertTable.getAlerts().first()[0]
        MatcherAssert.assertThat(result.startDate, Is.`is`(8562345623))
    }

    @Test
    fun deleteAlertByObject_deleteItem_returnZeroSize() = runBlockingTest {
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

    @Test
    fun deleteAlertById_deleteItem_returnZeroSize() = runBlockingTest {
        // Gavin
        val item = fakeData[0]
        alertTable.insertAlert(item)
        val items = alertTable.getAlerts().first()
        // When
        alertTable.deleteAlert(items[0].id ?: -1)
        // Then
        val result = alertTable.getAlerts().first()
        MatcherAssert.assertThat(result.size, Is.`is`(0))
    }
}