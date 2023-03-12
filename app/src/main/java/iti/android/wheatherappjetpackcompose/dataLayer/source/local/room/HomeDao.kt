package iti.android.wheatherappjetpackcompose.dataLayer.source.local.room


import androidx.room.*
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.HomeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HomeDao {
    @Query("SELECT * FROM home_table ORDER BY id DESC LIMIT 1")
    fun getHome(): Flow<HomeEntity>

    @Upsert
    suspend fun insertHome(home: HomeEntity)

    @Delete
    suspend fun deleteHome(home: HomeEntity)
}