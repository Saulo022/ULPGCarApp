package com.saulo.ulpgcarapp.domain.use_cases.publish

import com.saulo.ulpgcarapp.domain.model.Publish
import com.saulo.ulpgcarapp.domain.repository.PublishRepository
import javax.inject.Inject

class DeletePublishRide @Inject constructor(private val repository: PublishRepository) {

    suspend operator fun invoke(idPublishRide: String) = repository.delete(idPublishRide)

}