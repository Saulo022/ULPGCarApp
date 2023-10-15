package com.saulo.ulpgcarapp.domain.repository

import com.saulo.ulpgcarapp.domain.model.Publish
import com.saulo.ulpgcarapp.domain.model.Response

interface PublishRepository {

    suspend fun create(publish: Publish): Response<Boolean>

}