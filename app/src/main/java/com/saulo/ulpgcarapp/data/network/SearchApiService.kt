package com.saulo.ulpgcarapp.data.network

import com.saulo.ulpgcarapp.data.network.response.Feature
import com.saulo.ulpgcarapp.data.network.response.Features
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

    suspend fun doRoute(apiKey: String, start: String, end: String) : List<Features> {
        return withContext(Dispatchers.IO){
            val response = searchApiClient.getRoute(apiKey, start, end)
            response.body()?.features ?: emptyList()
        }
    }

}