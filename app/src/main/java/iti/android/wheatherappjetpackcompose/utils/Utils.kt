package iti.android.wheatherappjetpackcompose.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import iti.android.wheatherappjetpackcompose.R
import iti.android.wheatherappjetpackcompose.presentationLayer.MainActivity
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

// NavHostFragment.findNavController(this).navigate(r.id)
fun findNavController(activity: Activity): NavController? {
    val navHostFragment =
        (activity as? MainActivity)?.supportFragmentManager?.findFragmentById(R.id.mainNavHostFragment) as? NavHostFragment
    return navHostFragment?.navController
}

fun convertLongToTime(time: Long, language: String): String {
    val date = Date(TimeUnit.SECONDS.toMillis(time))
    val format = SimpleDateFormat("h:mm a", Locale(language))
    return format.format(date)
}

fun convertLongToDayDate(time: Long, language: String): String {
    val date = Date(time)
    val format = SimpleDateFormat("d MMM, yyyy", Locale(language))
    return format.format(date)
}

fun getCurrentLocale(context: Context): Locale? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        context.resources.configuration.locales[0]
    } else {
        context.resources.configuration.locale
    }
}

fun convertCalenderToDayString(calendar: Calendar, language: String): String {
    return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale(language))
}


fun getSharedPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences(
        "MyAlert",
        Context.MODE_PRIVATE
    )
}


fun getIcon(imageString: String): Int {
    val imageInInteger: Int
    when (imageString) {
        "01d" -> imageInInteger = R.drawable.icon_01d
        "01n" -> imageInInteger = R.drawable.icon_01n
        "02d" -> imageInInteger = R.drawable.icon_02d
        "02n" -> imageInInteger = R.drawable.icon_02n
        "03n" -> imageInInteger = R.drawable.icon_03n
        "03d" -> imageInInteger = R.drawable.icon_03d
        "04d" -> imageInInteger = R.drawable.icon_04d
        "04n" -> imageInInteger = R.drawable.icon_04n
        "09d" -> imageInInteger = R.drawable.icon_09d
        "09n" -> imageInInteger = R.drawable.icon_09n
        "10d" -> imageInInteger = R.drawable.icon_10d
        "10n" -> imageInInteger = R.drawable.icon_10n
        "11d" -> imageInInteger = R.drawable.icon_11d
        "11n" -> imageInInteger = R.drawable.icon_11n
        "13d" -> imageInInteger = R.drawable.icon_13d
        "13n" -> imageInInteger = R.drawable.icon_13n
        "50d" -> imageInInteger = R.drawable.icon_50d
        "50n" -> imageInInteger = R.drawable.icon_50n
        else -> imageInInteger = R.drawable.clouds
    }
    return imageInInteger
}