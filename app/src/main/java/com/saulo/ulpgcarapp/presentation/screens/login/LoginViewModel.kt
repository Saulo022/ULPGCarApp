package com.saulo.ulpgcarapp.presentation.screens.login

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(): ViewModel() {

    var email: MutableState<String> = mutableStateOf("")

    var password: MutableState<String> = mutableStateOf("")
}