package com.saulo.ulpgcarapp.presentation.screens.publish_a_ride

import com.saulo.ulpgcarapp.data.network.response.Feature


data class PublishRideSearchState (
    var search: String = ""
)

data class PublishRideSearchListState (
    var searchList: List<Feature> = emptyList()
)

data class PublishRideSearchReturnState (
    var searchReturn: String = ""
)

data class PublishRideSearchReturnListState (
    var searchReturnList: List<Feature> = emptyList()
)