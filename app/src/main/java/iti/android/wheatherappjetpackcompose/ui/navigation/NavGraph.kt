@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalComposeUiApi::class)

package iti.android.wheatherappjetpackcompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import iti.android.wheatherappjetpackcompose.ui.features.home.HomeScreen
import iti.android.wheatherappjetpackcompose.ui.features.settings.SettingsScreen
import net.biteam.customerqurtoba.ui.LoginScreen
import net.biteam.customerqurtoba.ui.RegisterScreen

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }


        composable(route = Screen.Settings.route) {
            SettingsScreen(navController = navController)
        }

        composable(route = Screen.Login.route) { backStackEntry ->
            backStackEntry.arguments?.getString("user")?.let { LoginScreen(navController, it) }
        }

        composable(route = Screen.Register.route) {
            RegisterScreen(navController = navController)
        }


    }

}


/*
composable(route = Screen.Splash.route) {
            SplashScreen(navController = navController)
        }

        composable(route = Screen.Favorite.route) {
            FavoriteScreen(navController = navController)
        }

        composable(route = Screen.Alert.route) {
            AlertScreen(navController)
            //backStackEntry.arguments?.getString("serviceId")?.let { FawryListCode(it, navController) }
        }
 */