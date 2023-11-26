package com.saulo.ulpgcarapp.presentation.screens.chats

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saulo.ulpgcarapp.domain.model.Message
import com.saulo.ulpgcarapp.domain.model.Publish
import com.saulo.ulpgcarapp.domain.model.Response
import com.saulo.ulpgcarapp.domain.use_cases.auth.AuthUseCases
import com.saulo.ulpgcarapp.domain.use_cases.chat.ChatUseCases
import com.saulo.ulpgcarapp.domain.use_cases.chat.SendMessage
import com.saulo.ulpgcarapp.presentation.screens.login.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatUseCases: ChatUseCases,
    private val authUseCases: AuthUseCases
) : ViewModel() {

    //STATE FORM
    var state by mutableStateOf(ChatState())
        private set

    val currentUser = authUseCases.getCurrentUser()

    var chatResponse by mutableStateOf<Response<List<Message>>?>(null)
    var messageResponse by mutableStateOf<Response<Boolean>?>(null)

    init {
        getChatMessages()
    }

    fun getChatMessages() {
        viewModelScope.launch {
        chatResponse = Response.Loading
            chatUseCases.getChatMessages("KH4RDcjwA4u7JpNi5ONo").collect(){
                chatResponse = it
            }
        }
    }

    fun onMessageInput(msg: String) {
        state = state.copy(message = msg)
    }

    fun senMessage() {
        viewModelScope.launch {
            val msg = state.message
            messageResponse = Response.Loading
            val result = chatUseCases.sendMessage(currentUser?.uid ?: "",msg)
            messageResponse = result
        }
    }


}