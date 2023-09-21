package com.saulo.ulpgcarapp.presentation.screens.publish_a_ride

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saulo.ulpgcarapp.domain.use_cases.searches.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PublishRideViewModel @Inject constructor(private val searchUseCase: SearchUseCase) : ViewModel() {

    //STATE SEARCH FORM
    var state by mutableStateOf(PublishRideSearchState())
        private set

    //STATE SEARCH LIST FORM
    var stateList by mutableStateOf(PublishRideSearchListState())
        private set

    //STATE SEARCH FORM
    var stateReturn by mutableStateOf(PublishRideSearchReturnState())
        private set

    //STATE SEARCH LIST FORM
    var stateReturnList by mutableStateOf(PublishRideSearchReturnListState())
        private set

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    //Metodos de ida
    fun onSearchInput(search: String) {
        state = state.copy(search = search)
    }

    fun onSearchDelete() {
        state = state.copy(search = "")
    }

    fun onSearchSelected() {
        viewModelScope.launch {
            val result = searchUseCase(
                apiKey = "5b3ce3597851110001cf6248430006dcbe134f72aea0f41e3b68d35b",
                text = state.search,
                long = "-15.4134300",
                lat = "28.0997300",
                radius = 50,
                country = "ES"
            )
            stateList = stateList.copy(searchList = result)
            Log.d("Saulo", "onSearchSelected: OK + $result")
        }
    }

    //Metodos de vuelta
    fun onSearchReturnInput(returnSearch: String) {
        stateReturn = stateReturn.copy(searchReturn = returnSearch)
    }

    fun onSearchReturnDelete() {
        stateReturn = stateReturn.copy(searchReturn = "")
    }

    fun onSearchReturnSelected() {
        viewModelScope.launch {
            val result = searchUseCase(
                apiKey = "5b3ce3597851110001cf6248430006dcbe134f72aea0f41e3b68d35b",
                text = stateReturn.searchReturn,
                long = "-15.4134300",
                lat = "28.0997300",
                radius = 50,
                country = "ES"
            )
            stateReturnList = stateReturnList.copy(searchReturnList = result)
            Log.d("Saulo", "onSearchSelected: OK + $result")
        }
    }

}