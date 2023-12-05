package com.saulo.ulpgcarapp.presentation.screens.passengers_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saulo.ulpgcarapp.domain.model.Passenger
import com.saulo.ulpgcarapp.domain.model.Publish
import com.saulo.ulpgcarapp.domain.model.Response
import com.saulo.ulpgcarapp.domain.use_cases.publish.PublishUseCases
import com.saulo.ulpgcarapp.presentation.screens.update_publish_ride.UpdatePublishRideState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PassengerListViewModel @Inject constructor(
    private val publishUseCases: PublishUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(PassengerListState())
        private set

    //ARGUMENTS
    val data = savedStateHandle.get<String>("publish")
    val publish = Publish.fromJson(data!!)

    var updatePassengerResponse by mutableStateOf<Response<Publish?>>(Response.Success(null))
        private set

    init {
        state = state.copy(
            pasajeros = publish.pasajeros
        )

        getPublishRideById(publish)
    }

    fun getPublishRideById(publishRide: Publish) {
        viewModelScope.launch {
            updatePassengerResponse = Response.Loading
            publishUseCases.getPublishRideById(publishRide).collect() {
                updatePassengerResponse = it
            }
        }
    }

    fun updatePublishRide(publish: Publish) {
        viewModelScope.launch {
            publishUseCases.updatePassengerRequest(publish)
        }
    }

    fun acceptPassengerRequest(passenger: Passenger) {
        for (pasajero in state.pasajeros) {
            if (pasajero.idPassenger == passenger.idPassenger) {
                pasajero.requestState = "Aceptada"
            }
        }
        onUpdateRide()
    }

    fun removePassenger(passenger: Passenger){
        for (pasajero in state.pasajeros) {
            if (pasajero.idPassenger == passenger.idPassenger) {
                state.pasajeros.remove(pasajero)
            }
        }
        onUpdateRide()
    }

    fun onUpdateRide() {
        val publish = Publish(
            id = publish.id,
            pasajeros = state.pasajeros
        )
        updatePublishRide(publish)
    }

}