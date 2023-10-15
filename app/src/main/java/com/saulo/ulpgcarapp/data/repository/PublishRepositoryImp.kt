package com.saulo.ulpgcarapp.data.repository

import com.google.firebase.firestore.CollectionReference
import com.saulo.ulpgcarapp.core.Constants
import com.saulo.ulpgcarapp.domain.model.Publish
import com.saulo.ulpgcarapp.domain.model.Response
import com.saulo.ulpgcarapp.domain.repository.PublishRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named

class PublishRepositoryImp @Inject constructor(@Named(Constants.PUBLISH) private val publishRef: CollectionReference): PublishRepository {

    override suspend fun create(publish: Publish): Response<Boolean> {
        return try {
            publishRef.add(publish).await()
            Response.Success(true)

        } catch (e: Exception) {
            e.printStackTrace()
            Response.Failure(e)
        }
    }

}