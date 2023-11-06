package com.saulo.ulpgcarapp.presentation.screens.request_a_ride

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.saulo.ulpgcarapp.presentation.screens.request_a_ride.components.RequestPublishRide
import com.saulo.ulpgcarapp.presentation.screens.request_a_ride.components.RequestRideContent

@Composable
fun RequestRideScreen(navController: NavHostController) {

    Scaffold(
        content = {
            RequestRideContent()
        }
    )
    RequestPublishRide()
}