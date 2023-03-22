package iti.android.wheatherappjetpackcompose.presentationLayer.ui.alert.services

import android.content.Context
import androidx.work.*
import iti.android.wheatherappjetpackcompose.dataLayer.repository.RepositoryImpl
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.AlertEntity
import iti.android.wheatherappjetpackcompose.dataLayer.source.dto.Weather
import iti.android.wheatherappjetpackcompose.domainLayer.models.WeatherDetailsCashMapper
import iti.android.wheatherappjetpackcompose.domainLayer.utils.TimeConverter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.*
import java.util.concurrent.TimeUnit

class AlertPeriodicWorkManger(private val context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    val repository = RepositoryImpl.getInstance(context)

    override suspend fun doWork(): Result {
        if (!isStopped) {
            val data = inputData
            val id = data.getLong("id", 0)
            getCurrentData(id.toInt())
        }
        return Result.success()
    }

    private suspend fun getCurrentData(id: Int) {
        val alertEntity = repository.getAlert(id)
        if (alertEntity == null) {
            return
        }
        val currentWeather =
            repository.getHome().map { WeatherDetailsCashMapper().mapFromEntity(it) }.first()

        val current = currentWeather.currentModel?.weather?.get(0) ?: Weather()
        if (checkTime(alertEntity)) {
            val delay: Long = getDelay(alertEntity)
            if (currentWeather.alerts.isEmpty()) {
                setOneTimeWorkManger(
                    delay,
                    alertEntity.id,
                    current.description,
                    current.icon
                )
            } else {
                setOneTimeWorkManger(
                    delay,
                    alertEntity.id,
                    currentWeather.alerts[0].tags[0],
                    current.icon
                )
            }
        } else {
            repository.deleteAlert(id)
            WorkManager.getInstance().cancelAllWorkByTag("$id")
        }
    }

    private fun setOneTimeWorkManger(delay: Long, id: Int?, description: String, icon: String) {
        val data = Data.Builder()
        data.putString("description", description)
        data.putString("icon", icon)
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()
        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(
            AlertOneTimeWorkManger::class.java,
        )
            .setInitialDelay(delay, TimeUnit.SECONDS)
            .setConstraints(constraints)
            .setInputData(data.build())
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "$id",
            ExistingWorkPolicy.REPLACE,
            oneTimeWorkRequest
        )
    }

    private fun getDelay(alert: AlertEntity): Long {
        val hour =
            TimeUnit.HOURS.toSeconds(Calendar.getInstance().get(Calendar.HOUR_OF_DAY).toLong())
        val minute =
            TimeUnit.MINUTES.toSeconds(Calendar.getInstance().get(Calendar.MINUTE).toLong())
        return (alert.startTime ?: 0) - ((hour + minute) - (2 * 3600L))
    }

    private fun checkTime(alert: AlertEntity): Boolean {
        val year = Calendar.getInstance().get(Calendar.YEAR)
        val month = Calendar.getInstance().get(Calendar.MONTH)
        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val date = "$day/${month + 1}/$year"
        val dayNow = TimeConverter.convertStringToTimestamp(date, TimeConverter.DATE_PATTERN_SLASH)
        return dayNow >= (alert.startDate ?: 0)
                &&
                dayNow <= (alert.endDate ?: 0)
    }

}