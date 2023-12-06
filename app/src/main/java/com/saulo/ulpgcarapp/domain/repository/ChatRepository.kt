package com.saulo.ulpgcarapp.domain.repository

import com.saulo.ulpgcarapp.domain.model.Message
import com.saulo.ulpgcarapp.domain.model.Publish
import com.saulo.ulpgcarapp.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    suspend fun sendMessage(msg: Message, publishId: String, photo: String, name: String): Response<Boolean>

    fun getChatMessages(publishId: String): Flow<Response<List<Message>>>
}