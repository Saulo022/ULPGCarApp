package com.saulo.ulpgcarapp.presentation.navigation


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.saulo.ulpgcarapp.presentation.screens.chats.ChatsScreen
import com.saulo.ulpgcarapp.presentation.screens.profile.ProfileScreen
import com.saulo.ulpgcarapp.presentation.screens.publish_a_ride.PublishRideScreen
import com.saulo.ulpgcarapp.presentation.screens.search.SearchScreen
import com.saulo.ulpgcarapp.presentation.screens.your_rides.YourRidesScreen

@Composable
fun HomeBottomBarNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = HomeBottomBarScreen.Search.route
    ) {

        composable(route = HomeBottomBarScreen.Search.route) {
            SearchScreen(navController)
        }

        composable(route = HomeBottomBarScreen.YourRides.route) {
            YourRidesScreen(navController)
        }

        composable(route = HomeBottomBarScreen.PublishRide.route) {
            PublishRideScreen(navController)
        }


        composable(route = HomeBottomBarScreen.Profile.route) {
            ProfileScreen(navController)
        }

        detailsNavGraph(navController = navController)

    }

}


sealed class HomeBottomBarScreen(
    val route: String,
    var title: String,
    val icon: ImageVector
) {

    object Search : HomeBottomBarScreen(
        route = "search",
        title = "Buscar",
        icon = Icons.Default.Search
    )

    object YourRides : HomeBottomBarScreen(
        route = "your_rides",
        title = "Tus viajes",
        icon = Icons.Default.DirectionsCar
    )

    object PublishRide : HomeBottomBarScreen(
        route = "publish_a_ride",
        title = "Publicar",
        icon = Icons.Default.AddBox
    )


    object Profile : HomeBottomBarScreen(
        route = "profile",
        title = "Perfil",
        icon = Icons.Default.Person
    )
}