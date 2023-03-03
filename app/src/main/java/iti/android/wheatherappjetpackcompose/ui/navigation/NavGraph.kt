package iti.android.wheatherappjetpackcompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import iti.android.wheatherappjetpackcompose.ui.features.alert.AlertScreen
import iti.android.wheatherappjetpackcompose.ui.features.favorite.FavoriteScreen
import iti.android.wheatherappjetpackcompose.ui.features.home.HomeScreen
import iti.android.wheatherappjetpackcompose.ui.features.settings.SettingsScreen
import iti.android.wheatherappjetpackcompose.ui.features.splash.SplashScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        composable(route = Screen.Home.route) { HomeScreen(navController = navController) }

        composable(route = Screen.Settings.route) { SettingsScreen(navController = navController) }

        composable(route = Screen.Alert.route) { AlertScreen(navController = navController) }

        composable(route = Screen.Favorite.route) { FavoriteScreen(navController = navController) }

        composable(route = Screen.Favorite.route) { SplashScreen(navController = navController) }
    }
}