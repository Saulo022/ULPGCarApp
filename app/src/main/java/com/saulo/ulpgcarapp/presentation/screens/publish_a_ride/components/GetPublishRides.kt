package com.saulo.ulpgcarapp.presentation.screens.publish_a_ride.components

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.saulo.ulpgcarapp.domain.model.Publish
import com.saulo.ulpgcarapp.domain.model.Response
import com.saulo.ulpgcarapp.presentation.components.ProgressBar
import com.saulo.ulpgcarapp.presentation.navigation.Graph
import com.saulo.ulpgcarapp.presentation.screens.publish_a_ride.PublishRideViewModel

@Composable
fun GetPublishRides(navController: NavHostController, viewModel: PublishRideViewModel = hiltViewModel()) {

    when(val response = viewModel.publishRidesResponse) {

        //MOSTRAR QUE SE ESTA REALIZANDO LA PETICION Y TODAVIA ESTA EN PROCESO
        Response.Loading -> {
            ProgressBar()
        }
        is Response.Success -> {
            PublishRideContent(navController = navController, publishRides = response.data)
        }

        is Response.Failure -> {

            Toast.makeText(LocalContext.current, response.exception?.message ?: "Error desconocido", Toast.LENGTH_LONG).show()
        }

        else -> {}
    }

}