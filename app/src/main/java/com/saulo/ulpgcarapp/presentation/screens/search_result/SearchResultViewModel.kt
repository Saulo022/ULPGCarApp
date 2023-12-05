package com.saulo.ulpgcarapp.presentation.screens.search_result

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saulo.ulpgcarapp.domain.model.Publish
import com.saulo.ulpgcarapp.domain.model.Response
import com.saulo.ulpgcarapp.domain.use_cases.auth.AuthUseCases
import com.saulo.ulpgcarapp.domain.use_cases.publish.PublishUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class SearchResultViewModel @Inject constructor(
    private val publishUseCases: PublishUseCases,
    private val savedStateHandle: SavedStateHandle,
    private val authUseCases: AuthUseCases
) : ViewModel() {

    //STATE PUBLISH SCREEN
    var state by mutableStateOf(SearchResultState())
        private set

    //ARGUMENTS
    val publishRides = savedStateHandle.get<String>("municipality")


    var publishRidesResponse by mutableStateOf<Response<List<Publish>>?>(null)

    var updatePublishRideResponse by mutableStateOf<Response<Boolean>?>(null)
        private set

    //USER SESSION
    val currentUser = authUseCases.getCurrentUser()

    init {
        state = state.copy(
            municipality = publishRides!!
        )

        getPublisRidesByMunicipality(state.municipality)
    }

    fun getPublisRidesByMunicipality(municipality: String) {
        viewModelScope.launch {
            publishRidesResponse = Response.Loading
            publishUseCases.getPublishRidesByMunicipality(municipality).collect() {
                publishRidesResponse = it
            }
        }
    }


    fun checkRequestState(publishRide: Publish): String {
        for (pasajero in publishRide.pasajeros) {
            if (pasajero.idPassenger == currentUser?.uid){
                if (pasajero.requestState == "Pendiente") {
                return "Pendiente"
                } else if (pasajero.requestState == "Aceptada"){
                    return "Aceptada"
                }
            }

        }
        return ""
    }

}