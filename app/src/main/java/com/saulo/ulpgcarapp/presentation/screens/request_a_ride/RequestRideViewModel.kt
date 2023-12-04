package com.saulo.ulpgcarapp.presentation.screens.request_a_ride

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saulo.ulpgcarapp.core.Constants
import com.saulo.ulpgcarapp.domain.model.Passenger
import com.saulo.ulpgcarapp.domain.model.Publish
import com.saulo.ulpgcarapp.domain.model.Response
import com.saulo.ulpgcarapp.domain.use_cases.auth.AuthUseCases
import com.saulo.ulpgcarapp.domain.use_cases.publish.PublishUseCases
import com.saulo.ulpgcarapp.domain.use_cases.searches.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestRideViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
    private val authUseCases: AuthUseCases,
    private val publishUseCases: PublishUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    //STATE REQUEST RIDE SCREEN
    var state by mutableStateOf(RequestRideState())
        private set

    //ARGUMENTS
    val data = savedStateHandle.get<String>("publish")
    val publish = Publish.fromJson(data!!)

    var requestPublishRideResponse by mutableStateOf<Response<Boolean>?>(null)
        private set

    //USER SESSION
    val currentUser = authUseCases.getCurrentUser()

    init {
        state = state.copy(
            pasajeros = publish.pasajeros,
            search = publish.origin,
            searchReturn = publish.destination,
            municipality = publish.municipio,
            timeChoose = publish.hora,
            dateChoose = publish.fecha,
            passengers = publish.numeroPasajeros,
            price = publish.precioViaje,
            route = publish.route
        )
    }

    fun onSearchInput(address: String) {
        state = state.copy(stopLocation = address)
    }

    fun onCoordinatesInput(longitude: String, latitude: String) {
        state = state.copy(stopLongitude = longitude, stopLatitude = latitude)
    }

    fun onSearchSelected() {
        viewModelScope.launch {
            val result = searchUseCase(
                apiKey = Constants.API_KEY,
                text = state.stopLocation,
                long = Constants.LONGITUDE,
                lat = Constants.LATITUDE,
                radius = Constants.RADIUS,
                country = Constants.COUNTRY
            )
            state = state.copy(searchList = result)
        }
    }

    fun onSearchDelete() {
        state = state.copy(stopLocation = "")
    }

    fun updatePublishRide(publish: Publish) {
        viewModelScope.launch {
            requestPublishRideResponse = Response.Loading
            val result = publishUseCases.updatePublishRide(publish)
            requestPublishRideResponse = result
        }
    }

    fun onUpdateRide() {
        val passenger = Passenger (
            idPassenger = currentUser?.uid ?: "",
            requestState = "pendiente",
            longitude = state.stopLongitude,
            latitude = state.stopLatitude
                )

        state.pasajeros.add(passenger)
        state.route.add("${state.stopLongitude},${state.stopLatitude}")


        val publish = Publish(
            id = publish.id,
            pasajeros = state.pasajeros,
            origin = publish.origin,
            destination = publish.destination,
            municipio = publish.municipio,
            hora = publish.hora,
            fecha = publish.fecha,
            numeroPasajeros = publish.numeroPasajeros,
            precioViaje = publish.precioViaje,
            route = state.route,
        )
        updatePublishRide(publish)
    }

}