package iti.android.wheatherappjetpackcompose.domainLayer.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

const val DATETIME_PATTERN = "dd MMM, hh:mm aa"
const val TIME_PATTERN = "hh aa"
const val DAY_PATTERN = "hh aa"

@SuppressLint("SimpleDateFormat")
fun dayConverterToString(dt: Int): String? {
    val timeStamp = dt.times(1000)
    return SimpleDateFormat(DAY_PATTERN).format(timeStamp)
}

@SuppressLint("SimpleDateFormat")
fun timeConverterToString(dt: Double): String? {
    val timeStamp = dt.times(1000)
    return SimpleDateFormat(TIME_PATTERN).format(timeStamp)
}

fun dateTimeConverterTimestampToString(dt: Double): String? {
    val timeStamp = dt.times(1000)
    return SimpleDateFormat(DATETIME_PATTERN).format(timeStamp)
}

fun iconConverter(icon: String): String = "https://openweathermap.org/img/wn/$icon@2x.png"

@SuppressLint("SimpleDateFormat")
fun dateTimeConverterStringToTimestamp(dt: String): Int {
    return SimpleDateFormat(DATETIME_PATTERN).parse(dt)?.time?.toInt() ?: 0

}
