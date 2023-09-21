package com.saulo.ulpgcarapp.data.network

import com.saulo.ulpgcarapp.data.network.response.Feature
import javax.inject.Inject

class SearchApiRepository @Inject constructor(private val searchApiService: SearchApiService) {

    suspend fun doSearch(apiKey: String, text: String, long: String, lat: String, radius: Int, country: String): List<Feature>{
        return searchApiService.doSearch(apiKey, text, long, lat, radius, country)
    }

}