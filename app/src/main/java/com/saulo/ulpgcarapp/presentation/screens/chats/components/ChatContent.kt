package com.saulo.ulpgcarapp.presentation.screens.chats.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Message
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.saulo.ulpgcarapp.R
import com.saulo.ulpgcarapp.domain.model.Message
import com.saulo.ulpgcarapp.presentation.components.DefaultButton
import com.saulo.ulpgcarapp.presentation.components.DefaultTextField
import com.saulo.ulpgcarapp.presentation.screens.chats.ChatViewModel
import com.saulo.ulpgcarapp.presentation.screens.chats.components.chat_components.ImageBubble
import com.saulo.ulpgcarapp.presentation.screens.chats.components.chat_components.MessageBubble

@Composable
fun ChatContent(
    viewModel: ChatViewModel = hiltViewModel(),
    chatMesssages: List<Message>,
    navController: NavHostController
) {

    val state = viewModel.state
    val focusRequester = remember { FocusRequester() }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(top = 70.dp)
        .systemBarsPadding()) {

        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp, bottom = 85.dp)
            .focusRequester(focusRequester)
        ) {

            items(items = chatMesssages) {
                ImageBubble(image = it.imagen, ownMessage = if (it.userId == (viewModel.currentUser?.uid  ?: "")) false else true, name = it.name)
                MessageBubble(message = it.contenido, ownMessage = if (it.userId == (viewModel.currentUser?.uid  ?: "")) false else true, hora = it.horaSimple)

            }
        }

        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
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