package com.saulo.ulpgcarapp.presentation.screens.driver_route

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.saulo.ulpgcarapp.core.Constants.API_KEY
import com.saulo.ulpgcarapp.data.network.response.Matrix
import com.saulo.ulpgcarapp.data.network.response.RouteResponse
import com.saulo.ulpgcarapp.domain.model.Publish
import com.saulo.ulpgcarapp.domain.model.Response
import com.saulo.ulpgcarapp.domain.use_cases.routes.RoutesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class DriveRouteViewModel @Inject constructor(
    private val routeUseCase: RoutesUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    //STATE PUBLISH SCREEN
    var state by mutableStateOf(DriveRouteState())
        private set

    //ARGUMENTS
    val data = savedStateHandle.get<String>("publish")
    val publish = Publish.fromJson(data!!)

    var driveRouteResponse by mutableStateOf<Response<Boolean>?>(null)
        private set


    init {
        state = state.copy(
            origin = publish.origin,
            destination = publish.destination,
            municipality = publish.municipio,
            timeChoose = publish.hora,
            dateChoose = publish.fecha,
            passengersList = publish.pasajeros,
            passengers = publish.numeroPasajeros,
            optimalRoute = publish.route,
            price = publish.precioViaje,
        )

        Log.d("Saulo", "PublishRideViewModelMatrixCoordinates999 + ${state.optimalRoute}")

        if (publish.pasajeros.size != 0){

            viewModelScope.launch {
                optimalRoute()
            }
        }

    }


    suspend fun getRoute(start: String, end: String): List<List<Double>> {
            driveRouteResponse = Response.Loading
            val result = routeUseCase.getrouteUseCase(
                apiKey = API_KEY,
                start = start,
                end = end
            )
            state = state.copy(route = result)
            driveRouteResponse = null
        Log.d("Saulo", "MatrixCoordinates2 + ${state.route}")
        return result.features.firstOrNull()?.geometry?.coordinates ?: emptyList()
        }


    suspend fun optimalRoute() {
        val routeList : MutableList<List<Double>> = mutableListOf()
        for (i in 0 until state.optimalRoute.size-1) {
            Log.d("Saulo", "HOLA i = + ${i}")
            Log.d("Saulo", "HOLA i = + ${state.optimalRoute}")
            val h = viewModelScope.async {
                driveRouteResponse = Response.Loading
            getRoute(
                state.optimalRoute[i],
                state.optimalRoute[i+1]
            )
            }
            routeList.addAll(h.await())

            Log.d("Saulo", "MatrixCoordinates3 + ${routeList}")

            val poly: MutableList<com.google.android.gms.maps.model.LatLng> = mutableListOf()
            routeList.forEach {
                poly.add(LatLng(it[1], it[0]))
            }
            state = state.copy(polyline = poly)

        }
    }


}