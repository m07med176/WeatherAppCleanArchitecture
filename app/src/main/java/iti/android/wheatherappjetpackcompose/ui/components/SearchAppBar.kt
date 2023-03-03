package iti.android.wheatherappjetpackcompose.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@ExperimentalComposeUiApi
@Composable
fun SearchAppBar(
) {

    var query by remember {
        mutableStateOf("")
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp), elevation = 8.dp,
        shape = RoundedCornerShape(32.dp)
    ) {
        TextField(
            value = query,
            onValueChange = {
                query = it
            },
            label = { Text(text = "Search") },

            shape = RoundedCornerShape(10.dp),
            leadingIcon = {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = "Search Icon"
                )
            },
            textStyle = TextStyle(color = MaterialTheme.colors.primary),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface
            ),
        )
    }
}



