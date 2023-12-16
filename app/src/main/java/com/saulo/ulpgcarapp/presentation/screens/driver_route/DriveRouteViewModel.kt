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
import com.saulo.ulpgcarapp.domain.model.Location
import com.saulo.ulpgcarapp.domain.model.Passenger
import com.saulo.ulpgcarapp.domain.model.Publish
import com.saulo.ulpgcarapp.domain.model.Response
import com.saulo.ulpgcarapp.domain.use_cases.publish.PublishUseCases
import com.saulo.ulpgcarapp.domain.use_cases.routes.RoutesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.math.roundToLong

@HiltViewModel
class DriveRouteViewModel @Inject constructor(
    private val routeUseCase: RoutesUseCases,
    private val publishUseCases: PublishUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    //STATE PUBLISH SCREEN
    var state by mutableStateOf(DriveRouteState())
        private set

    var publishResponse by mutableStateOf<Response<Publish?>>(Response.Success(null))
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
            price = publish.precioViaje,
        )

        viewModelScope.launch {
            getRouteTime()
        }


       viewModelScope.launch {
           optimalRoute()
       }


    }

    fun getlocations(): MutableList<Passenger> {
        val route: MutableList<Passenger> = mutableListOf()
        for (pasajero in state.passengersList) {
            route.add(pasajero)
        }
        val driverOrigin = Passenger(
            idPassenger = "",
            requestState = "",
            longitude = publish.origin.longitude,
            latitude = publish.origin.latitude,
            placeName = publish.origin.label,
            expectedTime = 0.0
        )

        route.add(0, driverOrigin)

        val driverDestination = Passenger(
            idPassenger = "",
            requestState = "",
            longitude = publish.destination.longitude,
            latitude = publish.destination.latitude,
            placeName = publish.destination.label,
            expectedTime = publish.destination.expectedTime
        )

        route.add(driverDestination)

        return route
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
        val routeList: MutableList<List<Double>> = mutableListOf()
        if (publish.pasajeros.size != 0) {
            for (pasajeros in state.passengersList) {
                state.optimalRoute.add("${pasajeros.longitude}, ${pasajeros.latitude}")
            }
        }

        state.optimalRoute.add(0, "${state.origin.longitude}, ${state.origin.latitude}")
        state.optimalRoute.add("${state.destination.longitude}, ${state.destination.latitude}")

        for (i in 0 until state.optimalRoute.size - 1) {
            val h = viewModelScope.async {
                driveRouteResponse = Response.Loading
                getRoute(
                    state.optimalRoute[i],
                    state.optimalRoute[i + 1]
                )
            }
            routeList.addAll(h.await())

            Log.d("Saulo", "MatrixCoordinates2 + ${state.optimalRoute}")
            Log.d("Saulo", "MatrixCoordinates3 + ${routeList}")

            val poly: MutableList<com.google.android.gms.maps.model.LatLng> = mutableListOf()
            routeList.forEach {
                poly.add(LatLng(it[1], it[0]))
            }
            state = state.copy(polyline = poly)

        }
    }

    suspend fun getMatrix(
        startLongitude: String,
        startLatitude: String,
        endLongitude: String,
        endLatitude: String
    ) = suspendCoroutine<Unit> {
        viewModelScope.launch {
            val matrixCoordinates: MutableList<List<Double>> = mutableListOf()
            matrixCoordinates.add(listOf(startLongitude.toDouble(), startLatitude.toDouble()))
            matrixCoordinates.add(listOf(endLongitude.toDouble(), endLatitude.toDouble()))
            val result = routeUseCase.matrixUseCase(matrix = Matrix(locations = matrixCoordinates))
            Log.d("Saulo", "RouteTime1 + ${result[0][1]}")
            state = state.copy(stopTime = result[0][1])
            Log.d("Saulo", "RouteTime2 + ${state.stopTime}")
            it.resume(Unit)
        }
    }

    suspend fun getRouteTime() {
        val optimalRoute: MutableList<Passenger> = getlocations()
        val routeTimes: MutableList<Double> = mutableListOf()
        routeTimes.add(0.0)
        for (i in 0 until optimalRoute.size - 1) {
            getMatrix(
                optimalRoute[i].longitude,
                optimalRoute[i].latitude,
                optimalRoute[i+1].longitude,
                optimalRoute[i+1].latitude
            )
            routeTimes.add(state.stopTime)
        }
        Log.d("Saulo", "RouteTime + ${routeTimes}")
        state = state.copy(routeTimes = routeTimes)
        Log.d("Saulo", "RouteTime4 + ${state.routeTimes}")
    }

}

