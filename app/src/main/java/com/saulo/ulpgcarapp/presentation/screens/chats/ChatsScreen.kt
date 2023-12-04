package com.saulo.ulpgcarapp.presentation.screens.chats

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.saulo.ulpgcarapp.presentation.screens.chats.components.ChatContent
import com.saulo.ulpgcarapp.presentation.screens.chats.components.GetChatMessages
import com.saulo.ulpgcarapp.presentation.screens.chats.components.SendMessage

@Composable
fun ChatsScreen(navController: NavHostController, id: String) {

    Scaffold(
        content = {
            GetChatMessages(navController)
        }
    )
    SendMessage()
}