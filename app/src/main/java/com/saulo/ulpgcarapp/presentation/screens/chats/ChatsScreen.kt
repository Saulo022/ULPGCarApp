package com.saulo.ulpgcarapp.presentation.screens.chats

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun ChatsScreen(navController: NavHostController) {

    Scaffold(
        content = {
            Text(text = "ChatsScreen")
        }
    )

}