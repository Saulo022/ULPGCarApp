package com.saulo.ulpgcarapp.presentation.screens.update_publish_ride

import com.saulo.ulpgcarapp.data.network.response.Feature
import com.saulo.ulpgcarapp.domain.model.Location

data class UpdatePublishRideState (
    var search: Location = Location("","",""),
    var searchList: List<Feature> = emptyList(),
    var municipality: String = "",
    var searchReturn: Location = Location("","","",0.0),
    var searchReturnList: List<Feature> = emptyList(),
    var timeChoose: String = "",
    var dateChoose: String = "",
    var passengers: Int = 1,
    var price: Int = 1,
    var stopTime: Double = 0.0,
    var plate: String = "",
    var availableSeat: Int = 0,
)

