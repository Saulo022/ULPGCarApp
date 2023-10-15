package com.saulo.ulpgcarapp.domain.repository

import com.saulo.ulpgcarapp.domain.model.Publish
import com.saulo.ulpgcarapp.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface PublishRepository {

    suspend fun create(publish: Publish): Response<Boolean>
    fun getPublishRides(): Flow<Response<List<Publish>>>
    fun getPublishRidesByUserId(idUser: String): Flow<Response<List<Publish>>>

}