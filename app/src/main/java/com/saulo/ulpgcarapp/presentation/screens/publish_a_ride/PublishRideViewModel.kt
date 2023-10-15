package com.saulo.ulpgcarapp.presentation.screens.publish_a_ride

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saulo.ulpgcarapp.domain.model.Publish
import com.saulo.ulpgcarapp.domain.model.Response
import com.saulo.ulpgcarapp.domain.use_cases.publish.PublishUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PublishRideViewModel @Inject constructor(
    private val publishUseCases: PublishUseCases
) : ViewModel() {

    var publishRidesResponse by mutableStateOf<Response<List<Publish>>?>(null)

    init {
        getPublisRides()
    }

    fun getPublisRides() {
        viewModelScope.launch {
            publishRidesResponse = Response.Loading
            publishUseCases.getPublishRides().collect() {
                publishRidesResponse = it
            }
        }
    }
}