package iti.android.wheatherappjetpackcompose.dataLayer.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.AlertEntity
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.FavoriteEntity
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.HomeEntity


@Database(
    entities = [HomeEntity::class, AlertEntity::class, FavoriteEntity::class],
    version = 8,
    exportSchema = false
)
@TypeConverters(WeatherConverters::class, StringListConverters::class)
abstract class RoomDB : RoomDatabase() {
    abstract fun homeDao(): HomeDao
    abstract fun alertDao(): AlertDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var instance: RoomDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                RoomDB::class.java,
                "room.db"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}