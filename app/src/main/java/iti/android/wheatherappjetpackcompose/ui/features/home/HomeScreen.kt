package iti.android.wheatherappjetpackcompose.ui.features.home

import android.annotation.SuppressLint
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import iti.android.wheatherappjetpackcompose.R
import iti.android.wheatherappjetpackcompose.ui.ImageViewData
import iti.android.wheatherappjetpackcompose.ui.components.ImageLazy
import iti.android.wheatherappjetpackcompose.ui.components.ImageLazyGrid
import iti.android.wheatherappjetpackcompose.ui.components.SearchAppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val color by rememberInfiniteTransition()
        .animateColor(
            initialValue = MaterialTheme.colors.primary,
            targetValue = MaterialTheme.colors.secondary,
            animationSpec = infiniteRepeatable(
                animation = tween(2000),
                repeatMode = RepeatMode.Reverse
            )
        )
    val arrayList = listOf(
        ImageViewData("city1", R.drawable.city1),
        ImageViewData("city2", R.drawable.city2),
        ImageViewData("city3", R.drawable.city3),
        ImageViewData("city4", R.drawable.city4),
    )


    Scaffold(backgroundColor = color) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Card(
                Modifier
                    .weight(0.75f)
                    .padding(8.dp),
                shape = RoundedCornerShape(32.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    verticalArrangement = Arrangement.Center
                ) {


                    SearchAppBar()
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        textAlign = TextAlign.Start,
                        text = "Align Your Body", color = Color(0xFF808080)
                    )

                    LazyRow(Modifier.verticalScroll(rememberScrollState())) {
                        items(arrayList) { item ->
                            ImageLazy(item)
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        textAlign = TextAlign.Start,
                        text = "FAVORITE COLLECTIONS", color = Color(0xFF808080)
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .padding(10.dp)
                            .padding(bottom = 60.dp)
                    ) {
                        items(arrayList) {
                            ImageLazyGrid(it)
                        }


                    }


                    Spacer(modifier = Modifier.height(90.dp))


                }
            }
        }
    }

}