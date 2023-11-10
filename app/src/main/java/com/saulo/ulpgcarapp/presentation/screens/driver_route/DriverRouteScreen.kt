package com.saulo.ulpgcarapp.presentation.screens.driver_route

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.saulo.ulpgcarapp.presentation.screens.driver_route.components.DriverRouteContent
import com.saulo.ulpgcarapp.presentation.screens.driver_route.components.GetDriverRoute

@Composable
fun DriverRouteScreen(navController: NavHostController, publishRide: String) {
    
    Scaffold(
        content = {
            DriverRouteContent()
        }
    )
    GetDriverRoute()
}