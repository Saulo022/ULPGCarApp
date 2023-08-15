package com.saulo.ulpgcarapp.presentation.screens.profile

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.saulo.ulpgcarapp.presentation.components.DefaultButton
import com.saulo.ulpgcarapp.presentation.navigation.AppScreen

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
) {

    Scaffold(
        topBar = {},
        content = {
            DefaultButton(
                modifier = Modifier,
                text = "Cerrar sesion",
                onClick = {
                    viewModel.logout()
                    navController.navigate(route = AppScreen.Login.route) {
                        //ESTO ES PARA QUE UNA VEZ CERREMOS SESION BORRE ESTA PANTALLA
                        // DE LA PILA DE PANTALLAS ANTERIORES
                        popUpTo(AppScreen.Profile.route) { inclusive = true }
                    }
                }
            )
        },
        bottomBar = {}
    )
}