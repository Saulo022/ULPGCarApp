package com.saulo.ulpgcarapp.data.repository

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.saulo.ulpgcarapp.core.Constants
import com.saulo.ulpgcarapp.domain.model.Message
import com.saulo.ulpgcarapp.domain.model.Publish
import com.saulo.ulpgcarapp.domain.model.Response
import com.saulo.ulpgcarapp.domain.repository.ChatRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named

class ChatRepositoryImpl @Inject constructor(
    @Named(Constants.CHAT) private val chatRef: CollectionReference,
    @Named(Constants.PUBLISH) private val publishRef: CollectionReference,
    @Named(Constants.USERS) private val usersRef: CollectionReference
) : ChatRepository {

    override suspend fun sendMessage(msg: Message): Response<Boolean> {
        return try {

            publishRef.document("KH4RDcjwA4u7JpNi5ONo").collection("Chat").add(msg)
            Response.Success(true)

        } catch (e: Exception) {
            e.printStackTrace()
            Response.Failure(e)
        }

    }

    override fun getChatMessages(publishId: String): Flow<Response<List<Message>>> = callbackFlow {

        val snapshotListener = publishRef.document("KH4RDcjwA4u7JpNi5ONo").collection("Chat").orderBy("fecha").orderBy("hora")
            .addSnapshotListener { snapshot, e ->

                val chatResponse = if (snapshot != null) {
                    val messages = snapshot.toObjects(Message::class.java)
                    snapshot.documents.forEachIndexed { index, document ->
                        messages[index].docuId = document.id
                    }
                    Response.Success(messages)

                } else {
                    Response.Failure(e)
                }
                trySend(chatResponse)
            }
        awaitClose {
            snapshotListener.remove()
        }
    }


}