package com.saulo.ulpgcarapp.presentation.screens.search_result

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.saulo.ulpgcarapp.presentation.components.DefaultTopBar
import com.saulo.ulpgcarapp.presentation.screens.search_result.components.GetPublishRidesByMunicipality

@Composable
fun SearchResultScreen(navController: NavHostController) {

    Scaffold(
        topBar = {
            DefaultTopBar(
                title = "Solicitar plaza",
                upAvailable = true,
                navController = navController
            )
        },
        content = { GetPublishRidesByMunicipality(navController = navController)})
}