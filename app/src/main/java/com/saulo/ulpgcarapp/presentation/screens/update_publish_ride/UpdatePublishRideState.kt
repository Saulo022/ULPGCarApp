package com.saulo.ulpgcarapp.presentation.screens.update_publish_ride

import com.saulo.ulpgcarapp.data.network.response.Feature

data class UpdatePublishRideState (
    var search: String = "",
    var searchList: List<Feature> = emptyList(),
    var municipality: String = "",
    var searchReturn: String = "",
    var searchReturnList: List<Feature> = emptyList(),
    var timeChoose: String = "",
    var dateChoose: String = "",
    var passengers: Int = 1,
    var price: String = "1"
)

