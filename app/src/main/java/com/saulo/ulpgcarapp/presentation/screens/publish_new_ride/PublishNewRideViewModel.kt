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
import com.saulo.ulpgcarapp.domain.model.Location
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

    //STATE NEW PUBLISH SCREEN
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

    //STATE BUTTON ADD PASSENGER
    var isEnabledAddPassengerButton = true

    //STATE BUTTON REMOVE PASSENGER
    var isEnabledRemovePassengerButton = false

    //STATE BUTTON UP PRICE
    var isEnabledUpPriceButton = true

    //STATE BUTTON LOWER PRICE
    var isEnabledLowerPriceButton = false

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    //Metodos de ida
    fun onSearchInput(label:String, longitude: String, latitude: String) {
        val location = Location(label, longitude, latitude)
        state = state.copy(search = location)
    }

    fun onMunicipalityInput(localadmin: String) {
        state = state.copy(municipality = localadmin)
    }

    fun onSearchDelete() {
        state = state.copy(search = Location("","",""))
    }

    fun onSearchSelected() {
        viewModelScope.launch {
            val result = searchUseCase(
                apiKey = API_KEY,
                text = state.search.label,
                long = LONGITUDE,
                lat = LATITUDE,
                radius = RADIUS,
                country = COUNTRY
            )
            state = state.copy(searchList = result)
        }
    }

    //Metodos de vuelta
    fun onSearchReturnInput(label:String, longitude: String, latitude: String) {
        val location = Location(label, longitude, latitude)
        state = state.copy(searchReturn = location)
    }

    fun onSearchReturnDelete() {
        state = state.copy(searchReturn = Location("","",""))
    }

    fun onSearchReturnSelected() {
        viewModelScope.launch {
            val result = searchUseCase(
                apiKey = API_KEY,
                text = state.searchReturn.label,
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
            origin = state.search,
            municipio = state.municipality,
            destination = state.searchReturn,
            fecha = state.dateChoose,
            hora = state.timeChoose,
            numeroPasajeros = state.passengers,
            precioViaje = state.price,
            idUser = currentUser?.uid ?: ""
        )
        publishARide(publish)
    }

    fun clearForm() {
        state = state.copy(
            search = Location("","",""),
            searchReturn = Location("","",""),
            timeChoose = "",
            dateChoose = "",
            passengers = 1,
            price = 1
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

    //Metodos para el precio del viaje

    fun upPrice() {
        val price = state.price + 1
        state = state.copy(price = price)
        if (state.price > 1) {
            isEnabledLowerPriceButton = true
        }
    }

    fun lowerPrice() {
        val price = state.price - 1
        state = state.copy(price = price)
        if (state.price == 1) {
            isEnabledLowerPriceButton = false
        }
    }


}