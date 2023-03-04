package iti.android.wheatherappjetpackcompose.shared.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import iti.android.wheatherappjetpackcompose.utils.ImageViewData

@Composable
fun ImageLazy(imageViewData: ImageViewData) {
    Card(
        modifier = Modifier.padding(10.dp),
        shape = RoundedCornerShape(16.dp),
        content = {
            Column(
                modifier = Modifier
                    .background(Color.Blue)
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .padding(8.dp)
            ) {


                Image(
                    painter = painterResource(id = imageViewData.images),
                    contentDescription = null, contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape)
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Italic,
                    fontSize = 20.sp,
                    text = imageViewData.name, color = Color.White
                )


            }

        }
    )
}


@Composable
fun ImageLazyGrid(imageViewData: ImageViewData) {
    Card(
        modifier = Modifier
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        content = {
            Row(
                modifier = Modifier
                    .background(Color.LightGray)
                    .wrapContentHeight()
                    .width(70.dp)
            ) {


                Image(
                    painter = painterResource(id = imageViewData.images),
                    contentDescription = null, contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .border(0.dp, Color.Gray, RoundedCornerShape(16.dp))
                )
                Text(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp),
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Italic,
                    fontSize = 18.sp,
                    text = imageViewData.name, color = Color.Black
                )


            }

        }
    )
}

/*
@Preview
@Composable
fun ImageLazy(){
    Card(
        shape = RoundedCornerShape(16.dp),
        content = {
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .padding(8.dp)
            ) {


                    Image(
                        painter = painterResource(id = R.drawable.sky),
                        contentDescription = null
                        ,contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Gray, CircleShape)
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        textAlign = TextAlign.Center,
                        fontStyle = FontStyle.Italic,
                        fontSize = 20.sp,
                        text = "JetBack Compose ", color = Color.Gray)


                }

        }
    )
}

@Preview
@Composable
fun ImageLazyGrid(){
    Card(
        shape = RoundedCornerShape(16.dp),
        content = {
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .padding(8.dp)
            ) {


                Image(
                    painter = painterResource(id = R.drawable.sky),
                    contentDescription = null
                    ,contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape)
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Italic,
                    fontSize = 20.sp,
                    text = "JetBack Compose ", color = Color.Gray)


            }

        }
    )
}
 */