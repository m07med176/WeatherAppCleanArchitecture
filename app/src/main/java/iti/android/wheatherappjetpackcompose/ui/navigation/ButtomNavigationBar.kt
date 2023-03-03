package iti.android.wheatherappjetpackcompose.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import iti.android.weatherappcompose.ui.navigation.models.BottomMenuContent
import iti.android.wheatherappjetpackcompose.ui.theme.AquaBlue
import iti.android.wheatherappjetpackcompose.ui.theme.ButtonBlue
import iti.android.wheatherappjetpackcompose.ui.theme.DeepBlue


@Composable
fun Nav(
    items: List<BottomMenuContent>,
    modifier: Modifier = Modifier,
    activeHighlightColor: Color = ButtonBlue,
    activeTextColor: Color = Color.White,
    inactiveTextColor: Color = AquaBlue,
    navController: NavController?,
    onItemClick: (BottomMenuContent) -> Unit,
) {
    val backStackEntry = navController!!.currentBackStackEntryAsState()

    BottomNavigation(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp),
        backgroundColor = DeepBlue,
        elevation = 8.dp
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route

            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = Color.Green,
                unselectedContentColor = Color.Gray,
                icon = {
                    BottomMenuItem(
                        item = item,
                        isSelected = selected,
                        onItemClick = { onItemClick(item) },
                        activeHighlightColor = activeHighlightColor,
                        activeTextColor = activeTextColor,
                        inactiveTextColor = inactiveTextColor
                    )
                }
            )
        }
    }
}

@Composable
fun BottomMenuItem(
    item: BottomMenuContent,
    isSelected: Boolean = false,
    activeHighlightColor: Color = ButtonBlue,
    activeTextColor: Color = Color.White,
    inactiveTextColor: Color = AquaBlue,
    onItemClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable {
                onItemClick()
            }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(if (isSelected) activeHighlightColor else Color.Transparent)
                .padding(10.dp)
        ) {


            if (item.badgeCount > 0) {
                BadgedBox(
                    badge = {
                        Text(text = item.badgeCount.toString())
                    }
                ) {
                    Icon(
                        painter = painterResource(id = item.iconId),
                        contentDescription = item.title,
                        tint = if (isSelected) activeTextColor else inactiveTextColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
            } else {
                Icon(
                    painter = painterResource(id = item.iconId),
                    contentDescription = item.title,
                    tint = if (isSelected) activeTextColor else inactiveTextColor,
                    modifier = Modifier.size(20.dp)
                )
            }

        }
        Text(
            text = item.title,
            color = if (isSelected) activeTextColor else inactiveTextColor
        )
    }
}