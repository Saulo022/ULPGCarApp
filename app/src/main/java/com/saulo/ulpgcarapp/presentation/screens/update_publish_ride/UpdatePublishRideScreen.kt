package com.saulo.ulpgcarapp.presentation.screens.update_publish_ride


import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.saulo.ulpgcarapp.presentation.components.DefaultTopBar
import com.saulo.ulpgcarapp.presentation.screens.publish_new_ride.components.PublishNewRide
import com.saulo.ulpgcarapp.presentation.screens.update_publish_ride.components.UpdatePublishRide
import com.saulo.ulpgcarapp.presentation.screens.update_publish_ride.components.UpdatePublishRideContent


@Composable
fun UpdatePublishRideScreen(navController: NavHostController, publishRide: String) {

    Scaffold(
        topBar = {
            DefaultTopBar(title = "Editar Viaje", upAvailable = true, navController = navController)
        }, content = {
            //
            UpdatePublishRideContent()
        }
    )
    UpdatePublishRide()
}
