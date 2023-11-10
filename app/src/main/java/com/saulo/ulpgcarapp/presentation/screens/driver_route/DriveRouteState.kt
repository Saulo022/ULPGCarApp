package com.saulo.ulpgcarapp.presentation.screens.driver_route

import com.google.type.LatLng
import com.saulo.ulpgcarapp.data.network.response.Feature
import com.saulo.ulpgcarapp.data.network.response.Features
import com.saulo.ulpgcarapp.data.network.response.RouteResponse

import com.saulo.ulpgcarapp.domain.model.Location

data class DriveRouteState (
    var origin: Location = Location("","",""),
    var searchList: List<Feature> = emptyList(),
    var municipality: String = "",
    var destination: Location = Location("","",""),
    var searchReturnList: List<Feature> = emptyList(),
    var timeChoose: String = "",
    var dateChoose: String = "",
    var passengers: Int = 1,
    var price: Int = 1,
    var route: RouteResponse = RouteResponse(emptyList()),
    var polyline: List<com.google.android.gms.maps.model.LatLng> = emptyList()
)