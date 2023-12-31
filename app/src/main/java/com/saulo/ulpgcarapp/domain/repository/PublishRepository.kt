package com.saulo.ulpgcarapp.domain.repository

import com.saulo.ulpgcarapp.domain.model.Passenger
import com.saulo.ulpgcarapp.domain.model.Publish
import com.saulo.ulpgcarapp.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface PublishRepository {

    suspend fun create(publish: Publish): Response<Boolean>
    suspend fun delete(idPublishRide: String): Response<Boolean>
    suspend fun update(publish: Publish): Response<Boolean>
    suspend fun updatePassengerRequest(publish: Publish): Response<Boolean>
    fun getPublishRides(): Flow<Response<List<Publish>>>
    fun getPublishRidesByUserId(idUser: String): Flow<Response<List<Publish>>>
    fun getPublishRidesByPassengerId(idUser: String): Flow<Response<List<Publish>>>
    fun getPublishRidesByMunicipality(municipality: String): Flow<Response<List<Publish>>>
    fun getPublishRideById(publishRide: Publish): Flow<Response<Publish?>>
}