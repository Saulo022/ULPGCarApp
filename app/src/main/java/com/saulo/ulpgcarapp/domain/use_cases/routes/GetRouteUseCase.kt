package com.saulo.ulpgcarapp.domain.use_cases.routes

import com.saulo.ulpgcarapp.data.network.RouteApiRepository
import com.saulo.ulpgcarapp.data.network.response.RouteResponse
import javax.inject.Inject

class GetRouteUseCase @Inject constructor(private val repository: RouteApiRepository){

    suspend operator fun invoke(apiKey: String, start: String, end: String): RouteResponse {
        return repository.doRoute(apiKey, start, end)
    }

}