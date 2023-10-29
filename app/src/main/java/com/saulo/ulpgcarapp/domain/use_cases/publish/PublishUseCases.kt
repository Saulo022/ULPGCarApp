package com.saulo.ulpgcarapp.domain.use_cases.publish

data class PublishUseCases(
    val publish: PublishRide,
    val getPublishRides: GetPublishRides,
    val getPublishRidesById: GetPublishRidesById,
    val getPublishRidesByMunicipality: GetPublishRidesByMunicipality,
    val deletePublishRide: DeletePublishRide,
    val updatePublishRide: UpdatePublishRide
)