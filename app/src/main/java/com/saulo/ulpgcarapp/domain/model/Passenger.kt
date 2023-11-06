package com.saulo.ulpgcarapp.domain.model

data class Passenger(
    var idPassenger: String = "",
    var name: String = "",
    var surnames: String = "",
    var numTrips: String = "",
    var rating: Double  = 0.0,
    var requestState: String = "",
    var longitude: String = "",
    var latitude: String = ""
)
