package com.saulo.ulpgcarapp.presentation.screens.publish_a_ride

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saulo.ulpgcarapp.domain.model.Publish
import com.saulo.ulpgcarapp.domain.model.Response
import com.saulo.ulpgcarapp.domain.use_cases.auth.AuthUseCases
import com.saulo.ulpgcarapp.domain.use_cases.publish.PublishUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PublishRideViewModel @Inject constructor(
    private val publishUseCases: PublishUseCases,
    private val authUseCases: AuthUseCases
) : ViewModel() {

    var publishRidesResponse by mutableStateOf<Response<List<Publish>>?>(null)
    var deleteResponse by mutableStateOf<Response<Boolean>?>(null)
    val currentUser = authUseCases.getCurrentUser()

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
            publishUseCases.getPublishRidesById(currentUser?.uid ?: "").collect() {
                publishRidesResponse = it
            }
        }
    }
}