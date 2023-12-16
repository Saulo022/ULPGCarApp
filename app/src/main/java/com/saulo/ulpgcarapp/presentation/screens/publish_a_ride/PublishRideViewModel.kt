package com.saulo.ulpgcarapp.presentation.screens.publish_a_ride

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saulo.ulpgcarapp.data.network.response.Matrix
import com.saulo.ulpgcarapp.domain.model.*
import com.saulo.ulpgcarapp.domain.use_cases.auth.AuthUseCases
import com.saulo.ulpgcarapp.domain.use_cases.publish.PublishUseCases
import com.saulo.ulpgcarapp.domain.use_cases.routes.RoutesUseCases
import com.saulo.ulpgcarapp.domain.use_cases.users.UsersUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PublishRideViewModel @Inject constructor(
    private val publishUseCases: PublishUseCases,
    private val routeUseCase: RoutesUseCases,
    private val authUseCases: AuthUseCases
) : ViewModel() {

    //STATE PUBLISH SCREEN
    var state by mutableStateOf(PublishRideState())
        private set

    var publishRidesResponse by mutableStateOf<Response<List<Publish>>?>(null)
    var deleteResponse by mutableStateOf<Response<Boolean>?>(null)
    var updatePublishRideResponse by mutableStateOf<Response<Boolean>?>(null)
        private set

    val currentUser = authUseCases.getCurrentUser()

    var matrixTime: List<Double> = emptyList()
    var orderedStopsList: MutableList<String> = mutableListOf()



    init {
        getPublisRides()
    }

    fun delete(idPost: String) {
        viewModelScope.launch {
            deleteResponse = Response.Loading
            val result = publishUseCases.deletePublishRide(idPost)
            deleteResponse = result
        }
    }

    fun getPublisRides() {
        viewModelScope.launch {
            publishRidesResponse = Response.Loading
            publishUseCases.getPublishRidesByUserId(currentUser?.uid ?: "").collect() {
                publishRidesResponse = it
            }
        }
    }





}