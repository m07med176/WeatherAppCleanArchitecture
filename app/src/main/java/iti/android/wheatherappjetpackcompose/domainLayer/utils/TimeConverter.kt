package iti.android.wheatherappjetpackcompose.domainLayer.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class TimeConverter {
    companion object {
        const val DATETIME_PATTERN = "dd MMM, hh:mm aa"
        const val TIME_PATTERN_HOUR = "hh aa"
        const val TIME_PATTERN = "hh:mm aa"
        const val DAY_PATTERN = "dd MMM"
        const val DATE_PATTERN = "dd-MMM-yyy"
        const val DATE_PATTERN_SLASH = "dd-MMM-yyy"

        @SuppressLint("SimpleDateFormat")
        fun convertTimestampToString(dt: Long, type: String): String? {
            val timeStamp = Date(TimeUnit.SECONDS.toMillis(dt))
            return SimpleDateFormat(type).format(timeStamp)
        }

        @SuppressLint("SimpleDateFormat")
        fun convertStringToTimestamp(dt: String, type: String): Long {
            return SimpleDateFormat(type).parse(dt)?.time ?: 0
        }
    }
}