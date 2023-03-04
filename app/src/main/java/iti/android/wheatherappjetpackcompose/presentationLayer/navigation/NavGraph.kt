package iti.android.wheatherappjetpackcompose.presentationLayer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import iti.android.wheatherappjetpackcompose.features.alert.AlertScreen
import iti.android.wheatherappjetpackcompose.features.home.HomeScreen
import iti.android.wheatherappjetpackcompose.features.intro.SplashScreen
import iti.android.wheatherappjetpackcompose.presentationLayer.SettingsScreen
import iti.android.wheatherappjetpackcompose.presentationLayer.screens.favorite.FavoriteScreen

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

        composable(route = Screen.Splash.route) { SplashScreen(navController = navController) }
    }
}