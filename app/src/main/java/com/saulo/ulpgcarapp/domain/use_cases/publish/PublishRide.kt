package com.saulo.ulpgcarapp.domain.use_cases.publish

import com.saulo.ulpgcarapp.domain.model.Publish
import com.saulo.ulpgcarapp.domain.repository.PublishRepository
import javax.inject.Inject

class PublishRide @Inject constructor(private val repository: PublishRepository) {

    suspend operator fun invoke(publish: Publish) = repository.create(publish)

}