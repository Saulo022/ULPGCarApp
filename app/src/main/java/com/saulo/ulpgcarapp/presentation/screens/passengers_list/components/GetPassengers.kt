package com.saulo.ulpgcarapp.presentation.screens.passengers_list.components

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
import com.saulo.ulpgcarapp.presentation.screens.passengers_list.PassengerListViewModel
import com.saulo.ulpgcarapp.presentation.screens.publish_a_ride.PublishRideViewModel

@Composable
fun GetPassengers(viewModel: PassengerListViewModel = hiltViewModel()) {

    when(val response = viewModel.updatePassengerResponse) {

        //MOSTRAR QUE SE ESTA REALIZANDO LA PETICION Y TODAVIA ESTA EN PROCESO
        Response.Loading -> {
            ProgressBar()
        }
        is Response.Success -> {
            response.data?.let { PassengerListContent(publishRides = it) }
        }

        is Response.Failure -> {

            Toast.makeText(LocalContext.current, response.exception?.message ?: "Error desconocido", Toast.LENGTH_LONG).show()
        }

        else -> {}
    }

}