package com.saulo.ulpgcarapp.presentation.screens.passengers_list

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.saulo.ulpgcarapp.domain.model.Publish
import com.saulo.ulpgcarapp.presentation.components.DefaultTopBar
import com.saulo.ulpgcarapp.presentation.screens.passengers_list.components.GetPassengers
import com.saulo.ulpgcarapp.presentation.screens.passengers_list.components.PassengerListContent

@Composable
fun PassengerListScreen(navController: NavHostController, publishRides: String) {

    Scaffold(
        topBar = {
            DefaultTopBar(title = "Pasajeros", upAvailable = true, navController = navController)
        },
        content = {
            GetPassengers()
        }
    )

}