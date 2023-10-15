package com.saulo.ulpgcarapp.data.repository

import com.google.firebase.firestore.CollectionReference
import com.saulo.ulpgcarapp.core.Constants
import com.saulo.ulpgcarapp.domain.model.Publish
import com.saulo.ulpgcarapp.domain.model.Response
import com.saulo.ulpgcarapp.domain.model.User
import com.saulo.ulpgcarapp.domain.repository.PublishRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named

class PublishRepositoryImp @Inject constructor(
    @Named(Constants.PUBLISH) private val publishRef: CollectionReference,
    @Named(Constants.USERS) private val usersRef: CollectionReference
) : PublishRepository {

    override suspend fun create(publish: Publish): Response<Boolean> {
        return try {
            publishRef.add(publish).await()
            Response.Success(true)

        } catch (e: Exception) {
            e.printStackTrace()
            Response.Failure(e)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun getPublishRides(): Flow<Response<List<Publish>>> = callbackFlow {

        val snapshotListener = publishRef.addSnapshotListener { snapshot, e ->

            GlobalScope.launch(Dispatchers.IO) {

                val publishResponse = if (snapshot != null) {
                    val publications = snapshot.toObjects(Publish::class.java)

                    publications.map {
                        async {
                            it.user = usersRef.document(it.idUser).get().await()
                                .toObject(User::class.java)!!
                        }
                    }.forEach {
                        it.await()
                    }

                    Response.Success(publications)
                } else {
                    Response.Failure(e)
                }
                trySend(publishResponse)
            }
        }
        awaitClose {
            snapshotListener.remove()
        }
    }

}