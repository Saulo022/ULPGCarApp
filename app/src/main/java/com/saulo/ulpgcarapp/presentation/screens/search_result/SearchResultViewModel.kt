package com.saulo.ulpgcarapp.presentation.screens.search_result

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saulo.ulpgcarapp.domain.model.Publish
import com.saulo.ulpgcarapp.domain.model.Response
import com.saulo.ulpgcarapp.domain.use_cases.publish.PublishUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class SearchResultViewModel @Inject constructor(private val publishUseCases: PublishUseCases, private val savedStateHandle: SavedStateHandle): ViewModel() {

    //STATE PUBLISH SCREEN
    var state by mutableStateOf(SearchResultState())
        private set

    //ARGUMENTS
    val publishRides = savedStateHandle.get<String>("municipality")

    var publishRidesResponse by mutableStateOf<Response<List<Publish>>?>(null)


    init {
        state = state.copy(
            municipality = publishRides!!
        )

        getPublisRidesByMunicipality(state.municipality)
    }

    fun getPublisRidesByMunicipality(municipality: String) {
        viewModelScope.launch {
            publishRidesResponse = Response.Loading
            publishUseCases.getPublishRidesByMunicipality(municipality).collect() {
                publishRidesResponse = it
            }
        }
    }
}