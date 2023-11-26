package com.saulo.ulpgcarapp.presentation.screens.chats.components

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.saulo.ulpgcarapp.domain.model.Response
import com.saulo.ulpgcarapp.presentation.components.ProgressBar
import com.saulo.ulpgcarapp.presentation.screens.chats.ChatViewModel
import com.saulo.ulpgcarapp.presentation.screens.publish_a_ride.PublishRideViewModel

@Composable
fun SendMessage(viewModel: ChatViewModel = hiltViewModel()) {

    when(val response = viewModel.messageResponse) {

        //MOSTRAR QUE SE ESTA REALIZANDO LA PETICION Y TODAVIA ESTA EN PROCESO
        Response.Loading -> {
            ProgressBar()
        }
        is Response.Success -> {

            Toast.makeText(LocalContext.current, "El Mensaje se ha enviado correctamente", Toast.LENGTH_LONG).show()

        }

        is Response.Failure -> {

            Toast.makeText(LocalContext.current, response.exception?.message ?: "Error desconocido", Toast.LENGTH_LONG).show()
            Log.d("Saulo", "FAIL + ${response.exception?.message}")

        }

        else -> {}
    }

}