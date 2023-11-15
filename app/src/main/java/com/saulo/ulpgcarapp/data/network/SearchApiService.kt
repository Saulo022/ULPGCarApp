package com.saulo.ulpgcarapp.data.network

import com.saulo.ulpgcarapp.data.network.response.Feature
import com.saulo.ulpgcarapp.data.network.response.Features
import com.saulo.ulpgcarapp.data.network.response.Matrix
import com.saulo.ulpgcarapp.data.network.response.RouteResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchApiService @Inject constructor(private val searchApiClient: SearchApiClient) {

    suspend fun doSearch(apiKey: String, text: String, long: String, lat: String, radius: Int, country: String) : List<Feature> {
        return withContext(Dispatchers.IO){
            val response = searchApiClient.search(apiKey, text, long, lat, radius, country)
            response.body()?.features ?: emptyList()
        }
    }

    suspend fun doRoute(apiKey: String, start: String, end: String) : RouteResponse {
        return withContext(Dispatchers.IO){
            val response = searchApiClient.getRoute(apiKey, start, end)
            (response.body() ?: RouteResponse(emptyList()) as RouteResponse)
        }
    }

    suspend fun doOptimisedRoute(matrix: Matrix) : List<List<Double>> {
        return withContext(Dispatchers.IO){
            val response = searchApiClient.optimisedRoute(matrix)
            response.body()?.durations ?: emptyList()
        }
    }

}