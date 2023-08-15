package com.saulo.ulpgcarapp.presentation.screens.profile

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun ProfileScreen(navHostController: NavHostController) {

    Scaffold(
        topBar = {},
        content = {
            Text(text = "ProfileScreen")
        },
        bottomBar = {}
    )
}