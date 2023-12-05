package com.saulo.ulpgcarapp.domain.use_cases.publish

import com.saulo.ulpgcarapp.domain.repository.PublishRepository
import javax.inject.Inject

class GetPublishRidesByUserId @Inject constructor(private val repository: PublishRepository) {

    operator fun invoke(idUser: String) = repository.getPublishRidesByUserId(idUser)
}