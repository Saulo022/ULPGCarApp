package com.saulo.ulpgcarapp.domain.repository

import com.saulo.ulpgcarapp.domain.model.Response
import com.saulo.ulpgcarapp.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository {

    suspend fun create(user: User): Response<Boolean>

    fun getUSerById(id: String): Flow<User>

}