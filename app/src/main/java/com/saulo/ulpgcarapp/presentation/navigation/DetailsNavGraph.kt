package com.saulo.ulpgcarapp.presentation.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.saulo.ulpgcarapp.presentation.screens.chats.ChatsScreen
import com.saulo.ulpgcarapp.presentation.screens.driver_route.DriverRouteScreen
import com.saulo.ulpgcarapp.presentation.screens.passengers_list.PassengerListScreen
import com.saulo.ulpgcarapp.presentation.screens.profile_edit.ProfileEditScreen
import com.saulo.ulpgcarapp.presentation.screens.publish_new_ride.PublishNewRideScreen
import com.saulo.ulpgcarapp.presentation.screens.request_a_ride.RequestRideScreen
import com.saulo.ulpgcarapp.presentation.screens.search_result.SearchResultScreen
import com.saulo.ulpgcarapp.presentation.screens.update_publish_ride.UpdatePublishRideScreen


fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {

    navigation(
        route = Graph.DETAILS,
        startDestination = DetailsScreen.ProfileEdit.route
    ) {

        composable(
            route = DetailsScreen.DriverRoute.route,
            arguments = listOf(navArgument("publish") {
                type = NavType.StringType
            })
        ) {
            it.arguments?.getString("publish")?.let {
                DriverRouteScreen(navController, it)
            }
        }

        composable(route = DetailsScreen.RequestRide.route) {
            RequestRideScreen(navController = navController)
        }

        composable(route = DetailsScreen.SearchResults.route) {
            SearchResultScreen(navController = navController)
        }

        composable(route = DetailsScreen.PublishNewRide.route) {
            PublishNewRideScreen(navController = navController)
        }

        composable(
            route = DetailsScreen.ProfileEdit.route,
            arguments = listOf(navArgument("user") {
                type = NavType.StringType
            })
        ) {
            it.arguments?.getString("user")?.let {
                ProfileEditScreen(navController, it)
            }
        }

        composable(
            route = DetailsScreen.UpdatePublishRide.route,
            arguments = listOf(navArgument("publish") {
                type = NavType.StringType
            })
        ) {
            it.arguments?.getString("publish")?.let {
                UpdatePublishRideScreen(navController, it)
            }
        }

        composable(
            route = DetailsScreen.PublicationChat.route,
            arguments = listOf(navArgument("publish") {
                type = NavType.StringType
            })
        ) {
            it.arguments?.getString("publish")?.let {
                ChatsScreen(navController, it)
            }
        }

        composable(
            route = DetailsScreen.PassengerList.route,
            arguments = listOf(navArgument("publish") {
                type = NavType.StringType
            })
        ) {
            it.arguments?.getString("publish")?.let {
                PassengerListScreen(navController, it)
            }
        }

    }

}

sealed class DetailsScreen(val route: String) {

    object DriverRoute: DetailsScreen("publish/route/{publish}") {
        fun passPublishRide(publishRide: String) = "publish/route/$publishRide"
    }
    object RequestRide: DetailsScreen("search/request/{publish}") {
        fun passPublishRide(publishRide: String) = "search/request/$publishRide"
    }
    object SearchResults: DetailsScreen("search/results/{municipality}") {
        fun passMunicipality(municipality: String) = "search/results/$municipality"
    }
    object PublishNewRide: DetailsScreen("publish/new")
    object ProfileEdit: DetailsScreen("profile/edit/{user}") {
        fun passUser(user: String) = "profile/edit/$user"
    }

    object UpdatePublishRide: DetailsScreen("publish/update/{publish}") {
        fun passPublishRide(publishRide: String) = "publish/update/$publishRide"
    }

    object PublicationChat: DetailsScreen("publish/chat/{publish}") {
        fun passPublishRide(publishRide: String) = "publish/chat/$publishRide"
    }

    object PassengerList: DetailsScreen("publish/passengers/{publish}") {
        fun passPublishRide(publishRide: String) = "publish/passengers/$publishRide"
    }

}