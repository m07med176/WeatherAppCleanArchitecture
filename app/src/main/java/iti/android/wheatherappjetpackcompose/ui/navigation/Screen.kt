package iti.android.wheatherappjetpackcompose.ui.navigation

import androidx.annotation.DrawableRes
import iti.android.wheatherappjetpackcompose.R

sealed class Screen(
    val route: String,
    val name: String,
    @DrawableRes val image: Int = R.drawable.ic_filter,
) {
    object Home : Screen("home_screen", "Home", R.drawable.ic_home)
    object Settings : Screen("settings_screen", "Settings", R.drawable.ic_settings)
    object Login : Screen("login_screen/{user}", "Login")
    object Register : Screen("register_screen", "Register")
}

/*
    object Favorite : Screen("favorite_screen", "Favorite")
    object Alert : Screen("alert_screen", "Alert")
    object Splash : Screen("splash_screen", "Welcome")
 */