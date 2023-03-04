package iti.android.wheatherappjetpackcompose.features.intro

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

@Composable
fun SplashScreen(navController: NavController? = null) {
    Text(text = "Splash Screen")
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen()
}