package com.saulo.ulpgcarapp.presentation.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.saulo.ulpgcarapp.presentation.screens.profile_edit.ProfileEditScreen

fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {

    navigation(
        route = Graph.DETAILS,
        startDestination = DetailsScreen.ProfileEdit.route
    ) {

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

    }

}

sealed class DetailsScreen(val route: String) {

    object ProfileEdit: DetailsScreen("profile/edit/{user}") {
        fun passUser(user: String) = "profile/edit/$user"
    }

}