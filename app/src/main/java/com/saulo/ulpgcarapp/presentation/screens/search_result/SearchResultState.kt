package com.saulo.ulpgcarapp.presentation.screens.search_result

import com.saulo.ulpgcarapp.domain.model.Passenger

data class SearchResultState (
    var id: String = "",
    var municipality: String = "",
    var pasajeros: MutableList<Passenger> = mutableListOf()

)