package com.saulo.ulpgcarapp.presentation.screens.home

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.saulo.ulpgcarapp.presentation.navigation.HomeBottomBarNavGraph
import com.saulo.ulpgcarapp.presentation.navigation.HomeBottomBarScreen


@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {

    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) {
        HomeBottomBarNavGraph(navController = navController)
    }

}

@Composable
fun BottomBar(navController: NavHostController) {

    val screens = listOf(
        HomeBottomBarScreen.Search,
        HomeBottomBarScreen.YourRides,
        HomeBottomBarScreen.PublishRide,
        HomeBottomBarScreen.Chats,
        HomeBottomBarScreen.Profile,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val bottomBarDestination = screens.any { it.route == currentDestination?.route }

    if (bottomBarDestination) {

        NavigationBar {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }

}

@Composable
fun RowScope.AddItem(
    screen: HomeBottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {

    NavigationBarItem(
        label = { Text(text = screen.title) },
        icon = { Icon(imageVector = screen.icon, contentDescription = "Navigation icon") },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        colors = NavigationBarItemDefaults.colors(
            unselectedIconColor = Color.Gray,
            unselectedTextColor = Color.Gray
        ),
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )

}