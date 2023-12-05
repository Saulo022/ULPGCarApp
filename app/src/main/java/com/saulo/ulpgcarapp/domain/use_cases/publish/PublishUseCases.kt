package com.saulo.ulpgcarapp.domain.use_cases.publish

data class PublishUseCases(
    val publish: PublishRide,
    val getPublishRides: GetPublishRides,
    val getPublishRidesByUserId: GetPublishRidesByUserId,
    val getPublishRidesByPassengerId: GetPublishRidesByPassengerId,
    val getPublishRidesByMunicipality: GetPublishRidesByMunicipality,
    val deletePublishRide: DeletePublishRide,
    val updatePublishRide: UpdatePublishRide,
    val updatePassengerRequest: UpdatePassengerRequest,
    val getPublishRideById: GetPublishRideById
)