package com.saulo.ulpgcarapp.presentation.screens.driver_route.components

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.saulo.ulpgcarapp.domain.model.Response
import com.saulo.ulpgcarapp.presentation.components.ProgressBar
import com.saulo.ulpgcarapp.presentation.screens.driver_route.DriveRouteViewModel
import com.saulo.ulpgcarapp.presentation.screens.update_publish_ride.UpdatePublishRideViewModel

@Composable
fun GetDriverRoute(viewModel: DriveRouteViewModel = hiltViewModel()) {

    when(val response = viewModel.driveRouteResponse) {

        //MOSTRAR QUE SE ESTA REALIZANDO LA PETICION Y TODAVIA ESTA EN PROCESO
        Response.Loading -> {
            ProgressBar()
        }
        is Response.Success -> {
            Toast.makeText(LocalContext.current, "La Ruta se ha generado correctamente", Toast.LENGTH_LONG).show()

        }

        is Response.Failure -> {

            Toast.makeText(LocalContext.current, response.exception?.message ?: "Error desconocido", Toast.LENGTH_LONG).show()

        }

        else -> {}
    }

}