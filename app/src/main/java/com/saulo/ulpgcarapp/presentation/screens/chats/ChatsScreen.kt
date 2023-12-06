package com.saulo.ulpgcarapp.presentation.screens.chats

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.saulo.ulpgcarapp.presentation.components.ChatTopBar
import com.saulo.ulpgcarapp.presentation.screens.chats.components.ChatContent
import com.saulo.ulpgcarapp.presentation.screens.chats.components.GetChatMessages
import com.saulo.ulpgcarapp.presentation.screens.chats.components.SendMessage

@Composable
fun ChatsScreen(
    navController: NavHostController,
    id: String,
    viewModel: ChatViewModel = hiltViewModel()
) {

    Scaffold(
        topBar = {
            ChatTopBar(
                origin = viewModel.state.origin,
                destination = viewModel.state.destination,
                date = viewModel.state.date,
                time = viewModel.state.time
            )
        }, content = {
            GetChatMessages(navController)
        }
    )
    SendMessage()
}