package com.saulo.ulpgcarapp.presentation.screens.publish_a_ride

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.saulo.ulpgcarapp.presentation.screens.publish_a_ride.components.PublishRideContent

@Composable
fun PublishRideScreen(navController: NavHostController) {

    Scaffold(
        content = {
            PublishRideContent()
        }
    )

}