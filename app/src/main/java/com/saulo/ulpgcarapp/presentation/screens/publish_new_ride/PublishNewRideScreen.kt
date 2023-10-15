package com.saulo.ulpgcarapp.presentation.screens.publish_new_ride



import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.saulo.ulpgcarapp.presentation.screens.publish_new_ride.components.PublishNewRide
import com.saulo.ulpgcarapp.presentation.screens.publish_new_ride.components.PublishNewRideContent



@Composable
fun PublishNewRideScreen(navController: NavHostController) {

    Scaffold(
        content = {
            //
            PublishNewRideContent()
        }
    )

    PublishNewRide()

}
