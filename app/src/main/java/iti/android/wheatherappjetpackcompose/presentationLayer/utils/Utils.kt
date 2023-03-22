package iti.android.wheatherappjetpackcompose.presentationLayer.utils

import android.app.Activity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import iti.android.wheatherappjetpackcompose.R
import iti.android.wheatherappjetpackcompose.presentationLayer.MainActivity

// NavHostFragment.findNavController(this).navigate(r.id)
fun findNavController(activity: Activity): NavController? {
    val navHostFragment =
        (activity as? MainActivity)?.supportFragmentManager?.findFragmentById(R.id.mainNavHostFragment) as? NavHostFragment
    return navHostFragment?.navController
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