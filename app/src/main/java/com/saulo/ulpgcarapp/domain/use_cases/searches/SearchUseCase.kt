package com.saulo.ulpgcarapp.domain.use_cases.searches

import com.saulo.ulpgcarapp.data.network.SearchApiRepository
import com.saulo.ulpgcarapp.data.network.response.Feature
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val repository: SearchApiRepository) {

    suspend operator fun invoke(
        apiKey: String,
        text: String,
        long: String,
        lat: String,
        radius: Int,
        country: String
    ): List<Feature> {
        return repository.doSearch(apiKey, text, long, lat, radius, country)
    }

}