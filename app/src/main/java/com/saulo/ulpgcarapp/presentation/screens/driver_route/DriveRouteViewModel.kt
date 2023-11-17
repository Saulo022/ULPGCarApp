package com.saulo.ulpgcarapp.presentation.screens.driver_route

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.saulo.ulpgcarapp.core.Constants.API_KEY
import com.saulo.ulpgcarapp.data.network.response.Feature
import com.saulo.ulpgcarapp.data.network.response.Matrix
import com.saulo.ulpgcarapp.data.network.response.RouteResponse
import com.saulo.ulpgcarapp.domain.model.Publish
import com.saulo.ulpgcarapp.domain.model.Response
import com.saulo.ulpgcarapp.domain.use_cases.routes.RoutesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@OptIn(DelicateCoroutinesApi::class)
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

    var optimalRoute: RouteResponse = RouteResponse(emptyList())
    //var optimalRoute: MutableList<List<Double>> = mutableListOf()

    init {
        state = state.copy(
            origin = publish.origin,
            destination = publish.destination,
            municipality = publish.municipio,
            timeChoose = publish.hora,
            dateChoose = publish.fecha,
            passengersList = publish.pasajeros,
            passengers = publish.numeroPasajeros,
            price = publish.precioViaje
        )
        //getRoute()
        //getMatrixCoordinates()
        //getMatrix()
        viewModelScope.launch {
            optimalRoute()
        }


    }

    fun getMatrixCoordinates() {
        state.matrixCoordinates.add(
            listOf(
                state.origin.longitude.toDouble(),
                state.origin.latitude.toDouble()
            )
        )
        for (coordenadas in state.passengersList) {
            state.matrixCoordinates.add(
                listOf(
                    coordenadas.longitude.toDouble(),
                    coordenadas.latitude.toDouble()
                )
            )
        }
        Log.d("Saulo", "MatrixCoordinates + ${state.matrixCoordinates}")
    }

    fun getMatrix() {
        viewModelScope.launch {
            driveRouteResponse = Response.Loading
            val result = routeUseCase.matrixUseCase(
                matrix = Matrix(
                    locations = state.matrixCoordinates
                )
            )
            state = state.copy(matrixTime = result[0])
            Log.d("Saulo", "matrixTime + ${result[0]}")
            driveRouteResponse = null
            orderStops()
        }
    }

    fun orderStops() {
        if (state.passengersList.size >= 2) {
            val sortList = state.matrixTime
            var sortListIndex = 0
            val listOrderedStops: MutableList<List<Double>> = mutableListOf()
            var list: List<Double> = emptyList()


            for (duration in sortList.sorted()) {
                var notSortListIndex = 0
                for (coordinates in state.matrixTime) {
                    if (duration == coordinates && duration != 0.0) {
                        notSortListIndex = notSortListIndex - 1
                        list = listOf(
                            state.passengersList[notSortListIndex].longitude.toDouble(),
                            state.passengersList[notSortListIndex].latitude.toDouble()
                        )
                        listOrderedStops.add(list)
                    }
                    notSortListIndex = notSortListIndex + 1
                }
                sortListIndex = sortListIndex + 1
            }
            state = state.copy(listOrderedStops = listOrderedStops)
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
        //val routeList = RouteResponse(emptyList())
        val routeList : MutableList<List<Double>> = mutableListOf()
        for (i in 0 until state.passengersList.size-1) {
            val h = viewModelScope.async {
                driveRouteResponse = Response.Loading
            getRoute(
                "${state.passengersList[i].longitude},${state.passengersList[i].latitude}",
                "${state.passengersList[i+1].longitude},${state.passengersList[i+1].latitude}"
            )
            }
            routeList.addAll(h.await())
            //routeList = h.await()
            //routeList.features.first().geometry.coordinates = routeList.features.first().geometry.coordinates + h.await()
            //optimalRoute.features.first().geometry.coordinates = routeList
            Log.d("Saulo", "MatrixCoordinates3 + ${routeList}")
            val poly: MutableList<com.google.android.gms.maps.model.LatLng> = mutableListOf()
            routeList.forEach {
                poly.add(LatLng(it[1], it[0]))
            }
            state = state.copy(polyline = poly)

        }
        /*
        state = state.copy(route = routeList)
        val poly: MutableList<com.google.android.gms.maps.model.LatLng> = mutableListOf()
        state.route.features.first().geometry.coordinates.forEach {
            poly.add(LatLng(it[1], it[0]))
        }
        state = state.copy(polyline = poly)
                 */
    }

    /*
    fun getRoute() {
        viewModelScope.launch {
            driveRouteResponse = Response.Loading
            val result = routeUseCase.getrouteUseCase(
                apiKey = API_KEY,
                start = "${state.origin.longitude},${state.origin.latitude}",
                end = "${state.destination.longitude},${state.destination.latitude}"
            )
            state = state.copy(route = result)


            val poly: MutableList<com.google.android.gms.maps.model.LatLng> = mutableListOf()

            state.route.features.first().geometry.coordinates.forEach {
                poly.add(LatLng(it[1], it[0]))
            }
            state = state.copy(polyline = poly)
        }
        driveRouteResponse = null
    }
         */


}