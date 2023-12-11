package com.saulo.ulpgcarapp.presentation.screens.passengers_list

import com.saulo.ulpgcarapp.domain.model.Passenger

data class PassengerListState (
    var pasajeros: MutableList<Passenger> = mutableListOf(),
    var availableSeats: Int = 0
)