package com.example.getirlite.view.components.bars

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.getirlite.view.Controllers
import com.example.getirlite.view.NavigationItem
import com.example.getirlite.view.Screen
import com.example.getirlite.ui.theme.Divider

@Composable
fun BottomBar(navController: NavController) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val bottomBarRoutes = listOf(
        NavigationItem.ProductList.route,
        NavigationItem.Cart.route,
        NavigationItem.Search.route,
        NavigationItem.Profile.route,
        NavigationItem.FavouriteItems.route
    )

    val isBottomBarVisible = currentRoute in bottomBarRoutes

    val offset by animateDpAsState(
        targetValue = if (isBottomBarVisible) 0.dp else Screen.Dimensions.bottomBar + 5.dp,
        label = ""
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(0.dp),
        modifier = Modifier
            .offset {
                IntOffset(x = 0, y = offset.toPx().toInt())
            }
    ) {
        HorizontalDivider(
            color = Color.Divider,
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 2.dp)
        )

        Row(
            modifier = Modifier
                .shadow(elevation = 5.dp)
                .background(Color.White)
        ) {
            BottomBarItems.entries.forEach {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(width = Screen.width.div(BottomBarItems.entries.size), height = Screen.Dimensions.bottomBar)
                        .clickable(
                            onClick = {
                                navController.navigate(it.controller.name)
                            },
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        )
                ) {
                    Image(
                        imageVector = it.icon,
                        contentDescription = it.name,
                        colorFilter = if (currentRoute == it.controller.name) ColorFilter.tint(Color.Blue) else ColorFilter.tint(Color.DarkGray),
                        modifier = Modifier
                            .size(32.dp)
                    )
                }
            }

        }
    }
}

enum class BottomBarItems {
    home, search, profile;

    val icon: ImageVector
        get() = when (this) {
            home -> Icons.Rounded.Home
            search -> Icons.Rounded.Search
            profile -> Icons.Rounded.Person
        }

    val controller: Controllers
        get() = when (this) {
            home -> Controllers.productList
            search -> Controllers.search
            profile -> Controllers.profile
        }
}