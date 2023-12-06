package com.saulo.ulpgcarapp.presentation.screens.chats

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saulo.ulpgcarapp.domain.model.Message
import com.saulo.ulpgcarapp.domain.model.Publish
import com.saulo.ulpgcarapp.domain.model.Response
import com.saulo.ulpgcarapp.domain.model.User
import com.saulo.ulpgcarapp.domain.use_cases.auth.AuthUseCases
import com.saulo.ulpgcarapp.domain.use_cases.chat.ChatUseCases
import com.saulo.ulpgcarapp.domain.use_cases.chat.SendMessage
import com.saulo.ulpgcarapp.domain.use_cases.users.UsersUseCases
import com.saulo.ulpgcarapp.presentation.screens.login.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatUseCases: ChatUseCases,
    private val authUseCases: AuthUseCases,
    private val usersUseCases: UsersUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var userData by mutableStateOf(User())
        private set

    //STATE FORM
    var state by mutableStateOf(ChatState())
        private set

    val currentUser = authUseCases.getCurrentUser()

    var chatResponse by mutableStateOf<Response<List<Message>>?>(null)
    var messageResponse by mutableStateOf<Response<Boolean>?>(null)

    val data = savedStateHandle.get<String>("publish")
    val publish = Publish.fromJson(data!!)

    init {
        state = state.copy(
            rideId = publish.id,
            origin = publish.origin.label,
            destination = publish.destination.label,
            date = publish.fecha,
            time = publish.hora,
            participants = publish.pasajeros.size+1,
        )
        getChatMessages()
        getUserById()
    }

    @SuppressLint("SuspiciousIndentation")
    fun getChatMessages() {
        viewModelScope.launch {
        chatResponse = Response.Loading
            chatUseCases.getChatMessages(state.rideId).collect(){
                chatResponse = it
            }
        }
    }

    fun onMessageInput(msg: String) {
        state = state.copy(message = msg)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun senMessage() {
        viewModelScope.launch {
            val msg = state.message
            messageResponse = Response.Loading
            val result = chatUseCases.sendMessage(currentUser?.uid ?: "",msg, state.rideId, userData.image, userData.username)
            messageResponse = result
        }
    }

    private fun getUserById() {
        viewModelScope.launch {
            usersUseCases.getUserById(currentUser!!.uid).collect() {
                userData = it
            }
        }
    }

}