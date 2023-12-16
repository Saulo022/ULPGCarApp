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
import com.saulo.ulpgcarapp.data.network.response.Matrix
import com.saulo.ulpgcarapp.domain.model.Location
import com.saulo.ulpgcarapp.domain.model.Publish
import com.saulo.ulpgcarapp.domain.model.Response
import com.saulo.ulpgcarapp.domain.use_cases.auth.AuthUseCases
import com.saulo.ulpgcarapp.domain.use_cases.publish.PublishUseCases
import com.saulo.ulpgcarapp.domain.use_cases.routes.RoutesUseCases
import com.saulo.ulpgcarapp.domain.use_cases.searches.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdatePublishRideViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
    private val publishUseCases: PublishUseCases,
    private val authUseCases: AuthUseCases,
    private val routeUseCase: RoutesUseCases,
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

    //PLATE
    var isPlateValid by mutableStateOf(false)
        private set
    var plateErrMsg by mutableStateOf("")
        private set

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        state = state.copy(
            search = publish.origin,
            searchReturn = publish.destination,
            municipality = publish.municipio,
            timeChoose = publish.hora,
            dateChoose = publish.fecha,
            passengers = publish.numeroPasajeros,
            price = publish.precioViaje,
            plate = publish.plate,
            availableSeat = publish.plazasDisponibles
        )
        Log.d("Saulo", "onSearchSelected: OK +  ${publish.id}")

        if (state.price == 1) {
            isEnabledLowerPriceButton = false
        }
    }

    //Metodos de ida
    fun onSearchInput(label:String, longitude: String, latitude: String) {
        val location = Location(label, longitude, latitude,0.0)
        state = state.copy(search = location)
        Log.d("Publish2", "PublishRideContent + $state")
    }

    fun onMunicipalityInput(localadmin: String) {
        state = state.copy(municipality = localadmin)
        Log.d("Publish2", "PublishRideContent + $state")
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
            Log.d("Saulo", "onSearchSelected: OK + $result")
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
            origin = state.search,
            municipio = state.municipality,
            destination = state.searchReturn,
            fecha = state.dateChoose,
            hora = state.timeChoose,
            numeroPasajeros = state.passengers,
            precioViaje = state.price,
            idUser = currentUser?.uid ?: "",
            plate = state.plate,
            plazasDisponibles = state.availableSeat
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

    fun onPlateInput(plate: String) {
        state = state.copy(plate = plate)
    }

    fun validatePlate() {
        if (state.plate.length >= 7) {
            isPlateValid = true
            plateErrMsg = ""
        } else {
            isPlateValid = false
            plateErrMsg = "Al menos 7 caracteres"
        }

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

    fun getMatrix(longitude: String, latitude: String) {
        viewModelScope.launch {
            val matrixCoordinates: MutableList<List<Double>> = mutableListOf()
            matrixCoordinates.add(listOf(state.search.longitude.toDouble(), state.search.latitude.toDouble()))
            matrixCoordinates.add(listOf(longitude.toDouble(), latitude.toDouble()))
            val result = routeUseCase.matrixUseCase(matrix = Matrix(locations = matrixCoordinates))
            state.stopTime = result[0][1]
        }
    }

}