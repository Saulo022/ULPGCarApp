package com.saulo.ulpgcarapp.presentation.screens.search

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun SearchScreen(navController: NavHostController) {

    Scaffold(
        content = {
            Text(text = "SearchScreen")
        }
    )

}