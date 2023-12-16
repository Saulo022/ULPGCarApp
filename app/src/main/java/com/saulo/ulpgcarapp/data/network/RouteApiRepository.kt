package com.saulo.ulpgcarapp.data.network

import com.saulo.ulpgcarapp.data.network.response.Feature
import com.saulo.ulpgcarapp.data.network.response.Matrix
import com.saulo.ulpgcarapp.data.network.response.RouteResponse
import javax.inject.Inject

class RouteApiRepository @Inject constructor(private val routeApiService: RouteApiService) {

    suspend fun doSearch(apiKey: String, text: String, long: String, lat: String, radius: Int, country: String): List<Feature>{
        return routeApiService.doSearch(apiKey, text, long, lat, radius, country)
    }

    suspend fun doRoute(apiKey: String, start: String, end: String): RouteResponse {
        return routeApiService.doRoute(apiKey, start, end)
    }

    suspend fun doOptimisedRoute(matrix: Matrix): List<List<Double>>{
        return routeApiService.doOptimisedRoute(matrix)
    }

}