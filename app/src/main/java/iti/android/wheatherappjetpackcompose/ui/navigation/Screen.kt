package iti.android.wheatherappjetpackcompose.ui.navigation

import androidx.annotation.DrawableRes
import iti.android.wheatherappjetpackcompose.R

sealed class Screen(
    val route: String,
    val name: String,
    @DrawableRes val image: Int = R.drawable.ic_home,
) {
    object Home : Screen("home_screen", "Home", R.drawable.ic_home)
    object Settings : Screen("settings_screen", "Settings", R.drawable.ic_settings)
    object Favorite : Screen("favorite_screen", "Favorite", R.drawable.ic_favorite)
    object Alert : Screen("alert_screen", "Alert", R.drawable.ic_alert)
    object Splash : Screen("splash_screen", "Welcome")
}

