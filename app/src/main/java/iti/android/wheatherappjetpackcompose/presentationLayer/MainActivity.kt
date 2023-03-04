package iti.android.wheatherappjetpackcompose.presentationLayer

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.navigation.compose.rememberNavController
import iti.android.weatherappcompose.ui.navigation.models.BottomMenuContent
import iti.android.wheatherappjetpackcompose.presentationLayer.navigation.Nav
import iti.android.wheatherappjetpackcompose.presentationLayer.navigation.Screen
import iti.android.wheatherappjetpackcompose.presentationLayer.navigation.SetupNavGraph
import iti.android.wheatherappjetpackcompose.presentationLayer.theme.WheatherAppJetpackComposeTheme

class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WheatherAppJetpackComposeTheme {

                val navController = rememberNavController()
                Scaffold(
                    content = {
                        SetupNavGraph(navController = navController)
                    },
                    bottomBar = {
                        Nav(
                            items = listOf(
                                BottomMenuContent(
                                    Screen.Home.route,
                                    Screen.Home.name,
                                    Screen.Home.image
                                ),

                                BottomMenuContent(
                                    Screen.Favorite.route,
                                    Screen.Favorite.name,
                                    Screen.Favorite.image
                                ),

                                BottomMenuContent(
                                    Screen.Alert.route,
                                    Screen.Alert.name,
                                    Screen.Alert.image
                                ),

                                BottomMenuContent(
                                    Screen.Settings.route,
                                    Screen.Settings.name,
                                    Screen.Settings.image
                                )
                            ),
                            navController = navController
                        ) {
                            navController.navigate(it.route)
                        }
                    }
                )
            }
        }
    }
}

