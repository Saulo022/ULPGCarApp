package com.saulo.ulpgcarapp.presentation.screens.publish_new_ride

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saulo.ulpgcarapp.core.Constants.API_KEY
import com.saulo.ulpgcarapp.core.Constants.COUNTRY
import com.saulo.ulpgcarapp.core.Constants.LATITUDE
import com.saulo.ulpgcarapp.core.Constants.LONGITUDE
import com.saulo.ulpgcarapp.core.Constants.RADIUS
import com.saulo.ulpgcarapp.domain.model.Publish
import com.saulo.ulpgcarapp.domain.model.Response
import com.saulo.ulpgcarapp.domain.use_cases.auth.AuthUseCases
import com.saulo.ulpgcarapp.domain.use_cases.publish.PublishUseCases
import com.saulo.ulpgcarapp.domain.use_cases.searches.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PublishNewRideViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
    private val publishUseCases: PublishUseCases,
    private val authUseCases: AuthUseCases
) :
    ViewModel() {

    //STATE PUBLISH SCREEN
    var state by mutableStateOf(PublishNewRideState())
        private set

    var publishARideResponse by mutableStateOf<Response<Boolean>?>(null)
        private set

    //USER SESSION
    val currentUser = authUseCases.getCurrentUser()

    //CALENDAR
    var dateChoose = ""

    //CLOCK
    var timeChoose = ""

    //BUTTON
    var isEnabledAddPassengerButton = true

    //BUTTON
    var isEnabledRemovePassengerButton = false

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    //Metodos de ida
    fun onSearchInput(search: String) {
        state = state.copy(search = search)
        Log.d("Publish2", "PublishRideContent + $state")
    }

    fun onMunicipalityInput(localadmin: String) {
        state = state.copy(municipality = localadmin)
        Log.d("Publish2", "PublishRideContent + $state")
    }

    fun onSearchDelete() {
        state = state.copy(search = "")
    }

    fun onSearchSelected() {
        viewModelScope.launch {
            val result = searchUseCase(
                apiKey = API_KEY,
                text = state.search,
                long = LONGITUDE,
                lat = LATITUDE,
                radius = RADIUS,
                country = COUNTRY
            )
            state = state.copy(searchList = result)
            Log.d("Saulo", "onSearchSelected: OK + $result")
        }
    }

    //Metodos de vuelta
    fun onSearchReturnInput(returnSearch: String) {
        state = state.copy(searchReturn = returnSearch)
    }

    fun onSearchReturnDelete() {
        state = state.copy(searchReturn = "")
    }

    fun onSearchReturnSelected() {
        viewModelScope.launch {
            val result = searchUseCase(
                apiKey = API_KEY,
                text = state.searchReturn,
                long = LONGITUDE,
                lat = LATITUDE,
                radius = RADIUS,
                country = COUNTRY
            )
            state = state.copy(searchReturnList = result)
            Log.d("Saulo", "onSearchSelected: OK + $result")
        }
    }

    fun publishARide(publish: Publish) {
        viewModelScope.launch {
            publishARideResponse = Response.Loading
            val result = publishUseCases.publish(publish)
            publishARideResponse = result
        }
    }

    fun onNewRide() {
        val publish = Publish(
            origen = state.search,
            municipio = state.municipality,
            destino = state.searchReturn,
            fecha = state.dateChoose,
            hora = state.timeChoose,
            numeroPasajeros = state.passengers,
            idUser = currentUser?.uid ?: ""
        )
        publishARide(publish)
    }

    fun clearForm() {
        state = state.copy(
            search = "",
            searchReturn = "",
            timeChoose = "",
            dateChoose = "",
            passengers = 1
        )
        publishARideResponse = null
    }

    //Metodo para elegir fecha del viaje
    fun onDateSelected(dateChoose: String) {
        state = state.copy(dateChoose = dateChoose)
    }

    //Metodo para elegir hora del viaje
    fun onClockSelected(timeChoose: String) {
        state = state.copy(timeChoose = timeChoose)
    }

    //Metodos para el numero de pasajeros
    fun addPassenger() {
        state = state.copy(passengers = state.passengers + 1)
        if (state.passengers > 1) {
            isEnabledRemovePassengerButton = true
        }
        if (state.passengers == 5) {
            isEnabledAddPassengerButton = false
        }
    }

    fun removePassenger() {
        state = state.copy(passengers = state.passengers - 1)
        if (state.passengers < 5) {
            isEnabledAddPassengerButton = true
        }
        if (state.passengers == 1) {
            isEnabledRemovePassengerButton = false
        }
    }


}