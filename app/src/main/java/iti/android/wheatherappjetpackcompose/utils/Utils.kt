package iti.android.wheatherappjetpackcompose.utils

import android.app.Activity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import iti.android.wheatherappjetpackcompose.R
import iti.android.wheatherappjetpackcompose.presentationLayer.MainActivity

fun findNavController(activity: Activity): NavController? {
    val navHostFragment =
        (activity as? MainActivity)?.supportFragmentManager?.findFragmentById(R.id.mainNavHostFragment) as? NavHostFragment
    return navHostFragment?.navController
}