package com.saulo.ulpgcarapp.presentation.screens.your_rides

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.saulo.ulpgcarapp.presentation.components.DefaultTopBar
import com.saulo.ulpgcarapp.presentation.screens.your_rides.components.GetYourPublishRides

@Composable
fun YourRidesScreen(navController: NavHostController) {

    Scaffold(
        topBar = {
            DefaultTopBar(
                title = "Tus viajes solicitados",
                upAvailable = false,
                navController = navController
            )
        },
        content = {
            GetYourPublishRides(navController = navController)

        }
    )

}