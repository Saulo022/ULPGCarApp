package com.saulo.ulpgcarapp.domain.use_cases.routes

import com.saulo.ulpgcarapp.data.network.SearchApiRepository
import com.saulo.ulpgcarapp.data.network.response.Features
import javax.inject.Inject

class GetRouteUseCase @Inject constructor(private val repository: SearchApiRepository){

    suspend operator fun invoke(apiKey: String, start: String, end: String): List<Features> {
        return repository.doRoute(apiKey, start, end)
    }

}