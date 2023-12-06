package com.saulo.ulpgcarapp.presentation.screens.request_a_ride

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saulo.ulpgcarapp.core.Constants
import com.saulo.ulpgcarapp.data.network.response.Matrix
import com.saulo.ulpgcarapp.domain.model.*
import com.saulo.ulpgcarapp.domain.use_cases.auth.AuthUseCases
import com.saulo.ulpgcarapp.domain.use_cases.publish.PublishUseCases
import com.saulo.ulpgcarapp.domain.use_cases.routes.RoutesUseCases
import com.saulo.ulpgcarapp.domain.use_cases.searches.SearchUseCase
import com.saulo.ulpgcarapp.domain.use_cases.users.UsersUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestRideViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
    private val authUseCases: AuthUseCases,
    private val publishUseCases: PublishUseCases,
    private val routeUseCase: RoutesUseCases,
    private val usersUseCases: UsersUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var userData by mutableStateOf(User())
        private set

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
        getUserById()
    }

    private fun getUserById() {
        viewModelScope.launch {
            usersUseCases.getUserById(currentUser!!.uid).collect() {
                userData = it
            }
        }
    }

    fun onSearchInput(address: String) {
        state = state.copy(stopLocation = address)
    }

    fun onCoordinatesInput(longitude: String, latitude: String) {
        state = state.copy(stopLongitude = longitude, stopLatitude = latitude)
        getMatrix(publish.origin, longitude,latitude)
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

    fun getMatrix(origin: Location,longitude: String, latitude: String)  {
        viewModelScope.launch {
            val matrixCoordinates: MutableList<List<Double>> = mutableListOf()
            matrixCoordinates.add(listOf(origin.longitude.toDouble(),origin.latitude.toDouble()))
            matrixCoordinates.add(listOf(longitude.toDouble(),latitude.toDouble()))
            val result = routeUseCase.matrixUseCase(matrix = Matrix(locations = matrixCoordinates))
            state.stopTime = result[0][1]
        }
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
            requestState = "Pendiente",
            longitude = state.stopLongitude,
            latitude = state.stopLatitude,
            placeName = state.stopLocation,
            expectedTime = state.stopTime
                )

        state.pasajeros.add(passenger)
        state.pasajeros.sortBy { it.expectedTime }
        state.route.add("${state.stopLongitude},${state.stopLatitude}")

        val freeplaces = (publish.numeroPasajeros - (state.pasajeros.size))

        val publish = Publish(
            id = publish.id,
            pasajeros = state.pasajeros,
            origin = publish.origin,
            destination = publish.destination,
            municipio = publish.municipio,
            hora = publish.hora,
            fecha = publish.fecha,
            numeroPasajeros = freeplaces,
            precioViaje = publish.precioViaje,
            route = state.route,
        )
        updatePublishRide(publish)
    }

}