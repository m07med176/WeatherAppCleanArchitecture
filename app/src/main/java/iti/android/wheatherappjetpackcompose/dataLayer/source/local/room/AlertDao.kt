package iti.android.wheatherappjetpackcompose.dataLayer.source.local.room


import androidx.room.*
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.AlertEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlertDao {
    @Query("SELECT * FROM alert_table")
    fun getAlerts(): Flow<List<AlertEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlert(entity: AlertEntity): Long

    @Delete
    suspend fun deleteAlertByObject(entity: AlertEntity)

    @Query("DELETE FROM alert_table where id = :id")
    suspend fun deleteAlert(id: Int)

    @Query("SELECT * FROM alert_table WHERE id = :id")
    fun getAlert(id: Int): AlertEntity
}