package com.saulo.ulpgcarapp.data.network

import com.saulo.ulpgcarapp.data.network.response.Feature
import com.saulo.ulpgcarapp.data.network.response.Features
import com.saulo.ulpgcarapp.data.network.response.RouteResponse
import javax.inject.Inject

class SearchApiRepository @Inject constructor(private val searchApiService: SearchApiService) {

    suspend fun doSearch(apiKey: String, text: String, long: String, lat: String, radius: Int, country: String): List<Feature>{
        return searchApiService.doSearch(apiKey, text, long, lat, radius, country)
    }

    suspend fun doRoute(apiKey: String, start: String, end: String): RouteResponse {
        return searchApiService.doRoute(apiKey, start, end)
    }

}