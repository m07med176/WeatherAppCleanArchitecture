package iti.android.weatherappcompose.ui.navigation.models

import androidx.annotation.DrawableRes

data class BottomMenuContent(
    val route: String,
    val title: String,
    @DrawableRes val iconId: Int,
    val badgeCount: Int = 0,
)
