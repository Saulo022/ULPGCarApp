package com.saulo.ulpgcarapp.presentation.screens.your_rides

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saulo.ulpgcarapp.domain.model.Publish
import com.saulo.ulpgcarapp.domain.model.Response
import com.saulo.ulpgcarapp.domain.use_cases.auth.AuthUseCases
import com.saulo.ulpgcarapp.domain.use_cases.publish.PublishUseCases
import com.saulo.ulpgcarapp.domain.use_cases.routes.RoutesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class YourRidesViewModel @Inject constructor(
    private val publishUseCases: PublishUseCases,
    private val routeUseCase: RoutesUseCases,
    private val authUseCases: AuthUseCases
) : ViewModel()  {

    var yoursRidesResponse by mutableStateOf<Response<List<Publish>>?>(null)

    val currentUser = authUseCases.getCurrentUser()

    init {
        getPublisRides()
    }

    fun getPublisRides() {
        viewModelScope.launch {
            yoursRidesResponse = Response.Loading
            publishUseCases.getPublishRidesByPassengerId(currentUser?.uid ?: "").collect() {
                yoursRidesResponse = it
            }
        }
    }
}