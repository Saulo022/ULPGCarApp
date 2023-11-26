package com.saulo.ulpgcarapp.domain.use_cases.chat

data class ChatUseCases(
    val sendMessage: SendMessage,
    val getChatMessages: GetChatMessages
)