package iti.android.wheatherappjetpackcompose.domainLayer.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

const val DATETIME_PATTERN = "dd MMM, hh:mm aa"


@SuppressLint("SimpleDateFormat")
fun dateTimeConverterTimestampToString(dt: Int): String? {
    val timeStamp = dt.times(1000)
    return SimpleDateFormat(DATETIME_PATTERN).format(timeStamp)
}

@SuppressLint("SimpleDateFormat")
fun dateTimeConverterStringToTimestamp(dt: String): Int {
    return SimpleDateFormat(DATETIME_PATTERN).parse(dt)?.time?.toInt() ?: 0

}
