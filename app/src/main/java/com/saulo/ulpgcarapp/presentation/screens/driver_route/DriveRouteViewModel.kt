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

    suspend fun getMatrix( matrixCoordinates: MutableList<List<Double>>) {
            driveRouteResponse = Response.Loading
            val result = routeUseCase.matrixUseCase(
                matrix = Matrix(
                    locations = matrixCoordinates
                )
            )
            state = state.copy(matrixTime = result[0])
            Log.d("Saulo", "matrixTime + ${result[0]}")
            driveRouteResponse = null
    }

    fun orderStops(): MutableList<List<Double>> {
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
            listOrderedStops.add(0, listOf(state.origin.longitude.toDouble(),state.origin.latitude.toDouble()))
            listOrderedStops.add(listOf(state.destination.longitude.toDouble(),state.destination.latitude.toDouble()))
            state = state.copy(listOrderedStops = listOrderedStops)
            Log.d("Saulo", "matrixTime2 + ${listOrderedStops}")
        }
        return state.listOrderedStops
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
        getMatrixCoordinates()
        val f =  viewModelScope.async {
            getMatrix(state.matrixCoordinates)
        }
        orderStops()
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