package iti.android.wheatherappjetpackcompose.features.alert

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController

@Composable
fun AlertScreen(navController: NavHostController? = null) {
    Text(text = "Hello Alert")
}


@Preview
@Composable
fun AlertScreenPreview() {
    AlertScreen()
}