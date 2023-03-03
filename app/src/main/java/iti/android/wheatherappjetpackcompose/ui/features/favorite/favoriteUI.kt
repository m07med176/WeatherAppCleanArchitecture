package iti.android.wheatherappjetpackcompose.ui.features.favorite

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

@Composable
fun FavoriteScreen(navController: NavController? = null) {
    Text(text = "this is Favorite Screen")
}

@Preview
@Composable
fun FavoriteScreenPreview() {
    FavoriteScreen()
}