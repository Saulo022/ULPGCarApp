package com.saulo.ulpgcarapp.presentation.screens.your_rides

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun YourRidesScreen(navController: NavHostController) {

    Scaffold(
        content = {
            Text(text = "YourRidesScreen")

        }
    )

}