package com.saulo.ulpgcarapp.presentation.screens.request_a_ride

import com.saulo.ulpgcarapp.data.network.response.Feature
import com.saulo.ulpgcarapp.domain.model.Location
import com.saulo.ulpgcarapp.domain.model.Passenger

data class RequestRideState(
    //NEW PARAMETERS
    var stopLocation: String = "",
    var stopLongitude: String = "",
    var stopLatitude: String = "",
    var searchList: List<Feature> = emptyList(),
    var pasajeros: MutableList<Passenger> = mutableListOf(),
    var stopTime: Double = 0.0,
    var availableSeat: Int = 0,
    var plate: String = "",

    //RECIEVE PARAMETERS
    var search: Location = Location("","",""),
    var municipality: String = "",
    var searchReturn: Location = Location("","",""),
    var timeChoose: String = "",
    var dateChoose: String = "",
    var passengers: Int = 1,
    var price: Int = 1,
    var route: MutableList<String> = mutableListOf()

)
