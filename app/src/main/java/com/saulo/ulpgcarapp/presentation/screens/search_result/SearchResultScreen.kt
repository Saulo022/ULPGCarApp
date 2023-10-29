package com.saulo.ulpgcarapp.presentation.screens.search_result

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.saulo.ulpgcarapp.presentation.screens.search_result.components.GetPublishRidesByMunicipality

@Composable
fun SearchResultScreen(navController: NavHostController) {

    Scaffold(content = { GetPublishRidesByMunicipality(navController = navController)})
}