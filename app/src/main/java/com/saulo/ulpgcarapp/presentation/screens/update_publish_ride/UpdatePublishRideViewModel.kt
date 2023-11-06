package com.saulo.ulpgcarapp.presentation.screens.update_publish_ride

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
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
class UpdatePublishRideViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
    private val publishUseCases: PublishUseCases,
    private val authUseCases: AuthUseCases,
    private val savedStateHandle: SavedStateHandle
) :
    ViewModel() {

    //STATE PUBLISH SCREEN
    var state by mutableStateOf(UpdatePublishRideState())
        private set

    //ARGUMENTS
    val data = savedStateHandle.get<String>("publish")
    val publish = Publish.fromJson(data!!)

    var updatePublishRideResponse by mutableStateOf<Response<Boolean>?>(null)
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
    var isEnabledLowerPriceButton = true

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        state = state.copy(
            search = publish.origen,
            searchReturn = publish.destino,
            municipality = publish.municipio,
            timeChoose = publish.hora,
            dateChoose = publish.fecha,
            passengers = publish.numeroPasajeros,
            price = publish.precioViaje
        )
        Log.d("Saulo", "onSearchSelected: OK +  ${publish.id}")

        if (state.price == 1) {
            isEnabledLowerPriceButton = false
        }
    }

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

    fun updatePublishRide(publish: Publish) {
        viewModelScope.launch {
            updatePublishRideResponse = Response.Loading
            val result = publishUseCases.updatePublishRide(publish)
            updatePublishRideResponse = result
        }
    }

    fun onUpdateRide() {
        val publish = Publish(
            id = publish.id,
            origen = state.search,
            municipio = state.municipality,
            destino = state.searchReturn,
            fecha = state.dateChoose,
            hora = state.timeChoose,
            numeroPasajeros = state.passengers,
            precioViaje = state.price,
            idUser = currentUser?.uid ?: ""
        )
        updatePublishRide(publish)
    }

    fun clearForm() {
        /*
        state = state.copy(
            search = "",
            searchReturn = "",
            timeChoose = "",
            dateChoose = "",
            passengers = 1,
            price = "1"
        )
         */
        updatePublishRideResponse = null
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
        state = state.copy(price = state.price + 1)
        if (state.price > 1) {
            isEnabledLowerPriceButton = true
        }
    }

    fun lowerPrice() {
        state = state.copy(price = state.price - 1)
        if (state.price == 1) {
            isEnabledLowerPriceButton = false
        }
    }


}