package com.saulo.ulpgcarapp.domain.use_cases.routes

import com.saulo.ulpgcarapp.data.network.RouteApiRepository
import com.saulo.ulpgcarapp.data.network.response.Matrix
import javax.inject.Inject

class MatrixUseCase @Inject constructor(private val repository: RouteApiRepository) {

    suspend operator fun invoke(matrix: Matrix): List<List<Double>> {
        return repository.doOptimisedRoute(matrix)
    }

}