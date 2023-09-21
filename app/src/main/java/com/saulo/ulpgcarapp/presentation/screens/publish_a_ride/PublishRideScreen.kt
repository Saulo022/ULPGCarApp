package com.saulo.ulpgcarapp.presentation.screens.publish_a_ride



import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.saulo.ulpgcarapp.presentation.screens.publish_a_ride.components.PublishRideContent
import com.saulo.ulpgcarapp.presentation.screens.publish_a_ride.components.PublishRideContentWithSearch


@Composable
fun PublishRideScreen(navController: NavHostController) {

    Scaffold(
        content = {
            //PublishRideContentWithSearch()
            PublishRideContent()
        }
    )

}
