package com.saulo.ulpgcarapp.domain.use_cases.publish

import com.saulo.ulpgcarapp.domain.model.Publish
import com.saulo.ulpgcarapp.domain.repository.PublishRepository
import javax.inject.Inject

class GetPublishRideById @Inject constructor(private val repository: PublishRepository) {

    operator fun invoke(publishRide: Publish) = repository.getPublishRideById(publishRide)
}