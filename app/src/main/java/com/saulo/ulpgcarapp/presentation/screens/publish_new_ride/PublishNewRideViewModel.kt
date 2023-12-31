package com.saulo.ulpgcarapp.presentation.screens.publish_new_ride

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saulo.ulpgcarapp.core.Constants
import com.saulo.ulpgcarapp.core.Constants.API_KEY
import com.saulo.ulpgcarapp.core.Constants.COUNTRY
import com.saulo.ulpgcarapp.core.Constants.LATITUDE
import com.saulo.ulpgcarapp.core.Constants.LONGITUDE
import com.saulo.ulpgcarapp.core.Constants.RADIUS
import com.saulo.ulpgcarapp.data.network.response.Matrix
import com.saulo.ulpgcarapp.domain.model.*
import com.saulo.ulpgcarapp.domain.use_cases.auth.AuthUseCases
import com.saulo.ulpgcarapp.domain.use_cases.publish.PublishUseCases
import com.saulo.ulpgcarapp.domain.use_cases.routes.RoutesUseCases
import com.saulo.ulpgcarapp.domain.use_cases.searches.SearchUseCase
import com.saulo.ulpgcarapp.domain.use_cases.users.UsersUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PublishNewRideViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
    private val publishUseCases: PublishUseCases,
    private val authUseCases: AuthUseCases,
    private val routeUseCase: RoutesUseCases,
    private val usersUseCases: UsersUseCases
) :
    ViewModel() {

    //STATE NEW PUBLISH SCREEN
    var state by mutableStateOf(PublishNewRideState())
        private set


    private val _campusLocation = MutableLiveData<Campus>()
    val campusLocation: LiveData<Campus> = _campusLocation

    var publishARideResponse by mutableStateOf<Response<Boolean>?>(null)
        private set

    var userData by mutableStateOf(User())
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

    //PLATE
    var isPlateValid by mutableStateOf(false)
        private set
    var plateErrMsg by mutableStateOf("")
        private set

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    var listOfCampuses = Constants.campuses

    init {
        getUserById()

    }

    private fun getUserById() {
        viewModelScope.launch {
            usersUseCases.getUserById(currentUser!!.uid).collect() {
                userData = it
            }
        }
    }

    //Metodos de ida
    fun onSearchInput(label: String, longitude: String, latitude: String) {
        val location = Location(label, longitude, latitude, 0.0)
        state = state.copy(search = location)
    }

    fun onMunicipalityInput(localadmin: String) {
        state = state.copy(municipality = localadmin)
    }

    fun onSearchDelete() {
        state = state.copy(search = Location("", "", "", 0.0))
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
    fun onSearchReturnInput(label: String, longitude: String, latitude: String) {
        val location = Location(label, longitude, latitude)
        state = state.copy(searchReturn = location)
    }



    fun onSearchReturnDelete() {
        state = state.copy(searchReturn = Location("", "", "", 0.0))
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

    fun getMatrix(longitude: String, latitude: String) {
        viewModelScope.launch {
            val matrixCoordinates: MutableList<List<Double>> = mutableListOf()
            matrixCoordinates.add(listOf(state.search.longitude.toDouble(), state.search.latitude.toDouble()))
            matrixCoordinates.add(listOf(longitude.toDouble(), latitude.toDouble()))
            val result = routeUseCase.matrixUseCase(matrix = Matrix(locations = matrixCoordinates))
            state.stopTime = result[0][1]
        }
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

    fun onNewRide() {
        val location = Location(
            state.searchReturn.label,
            state.searchReturn.longitude,
            state.searchReturn.latitude,
            state.stopTime
        )
        state = state.copy(searchReturn = location)

        val locations: MutableList<String> = mutableListOf()
        locations.add("${state.search.longitude},${state.search.latitude}")
        locations.add("${state.searchReturn.longitude},${state.searchReturn.latitude}")
        state = state.copy(optimalRoute = locations)


        val publish = Publish(
            origin = state.search,
            municipio = state.municipality,
            destination = state.searchReturn,
            fecha = state.dateChoose,
            hora = state.timeChoose,
            numeroPasajeros = state.passengers,
            precioViaje = state.price,
            idUser = currentUser?.uid ?: "",
            route = state.optimalRoute,
            image = userData.image,
            plazasDisponibles = state.passengers,
            plate = state.plate
        )
        publishARide(publish)
    }

    fun clearForm() {
        state = state.copy(
            search = Location("", "", ""),
            searchReturn = Location("", "", ""),
            timeChoose = "",
            dateChoose = "",
            passengers = 1,
            price = 1,
            plate = ""
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

    fun onMenuInput(campusName: String) {
        for (campus in Constants.campuses) {
            if (campus.name == campusName) {
                _campusLocation.value = campus
                Log.d("Saulo", "EYY11 + ${_campusLocation.value?.name}")
                Log.d("Saulo", "EYY22 + ${campusLocation.value?.name}")
            }
        }
        Log.d("Saulo", "EYY33 + ${_campusLocation.value?.name}")
        Log.d("Saulo", "EYY44 + ${campusLocation.value?.name}")
    }

}