package com.saulo.ulpgcarapp.domain.use_cases.chat

import com.saulo.ulpgcarapp.domain.repository.ChatRepository
import javax.inject.Inject

class GetChatMessages @Inject constructor(private val repository: ChatRepository) {

    operator fun invoke(publishId:String) = repository.getChatMessages(publishId)
}