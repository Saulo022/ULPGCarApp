package com.saulo.ulpgcarapp.presentation.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.saulo.ulpgcarapp.core.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(): ViewModel() {

    //STATE SEARCH SCREEN
    var state by mutableStateOf(SearchState())
        private set

    var listOfMunicipalities = Constants.municipalities

    var expanded = false

    fun onMenuInput(municipality: String) {
        state = state.copy(municipality = municipality)
    }
}