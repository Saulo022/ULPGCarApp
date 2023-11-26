package com.saulo.ulpgcarapp.presentation.screens.chats.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.saulo.ulpgcarapp.domain.model.Message
import com.saulo.ulpgcarapp.domain.model.Publish
import com.saulo.ulpgcarapp.presentation.components.DefaultButton
import com.saulo.ulpgcarapp.presentation.components.DefaultTextField
import com.saulo.ulpgcarapp.presentation.screens.chats.ChatViewModel
import com.saulo.ulpgcarapp.presentation.screens.login.LoginViewModel

@Composable
fun ChatContent(
    viewModel: ChatViewModel = hiltViewModel(),
    chatMesssages: List<Message>,
    navController: NavHostController
) {

    val state = viewModel.state

    Box(modifier = Modifier.fillMaxWidth()) {

        LazyColumn(modifier = Modifier.fillMaxWidth()) {

            items(items = chatMesssages) {
                Text(text = it.contenido)
            }
        }

        Column(modifier = Modifier.padding(top = 600.dp)) {
            Row(modifier = Modifier.padding(horizontal = 10.dp)) {
                DefaultTextField(
                    modifier = Modifier.width(260.dp),
                    value = state.message,
                    onValueChange = { viewModel.onMessageInput(it) },
                    label = "Mensaje",
                    icon = Icons.Default.Message
                )
                Spacer(modifier = Modifier.width(10.dp))
                DefaultButton(modifier = Modifier
                    .height(65.dp)
                    .width(65.dp),
                    text = "Enviar Mensaje",
                    onClick = {
                        viewModel.senMessage()
                        state.message = ""
                    })
            }
        }

    }
}