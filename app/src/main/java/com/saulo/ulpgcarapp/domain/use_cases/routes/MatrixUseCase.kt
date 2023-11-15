package com.saulo.ulpgcarapp.domain.use_cases.routes

import com.saulo.ulpgcarapp.data.network.SearchApiRepository
import com.saulo.ulpgcarapp.data.network.response.Matrix
import javax.inject.Inject

class MatrixUseCase @Inject constructor(private val repository: SearchApiRepository) {

    suspend operator fun invoke(matrix: Matrix): List<List<Double>> {
        return repository.doOptimisedRoute(matrix)
    }

}