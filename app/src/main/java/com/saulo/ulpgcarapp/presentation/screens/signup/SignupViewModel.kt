package com.saulo.ulpgcarapp.presentation.screens.signup

import android.util.Patterns
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor() : ViewModel() {

    //USERNAME
    var username: MutableState<String> = mutableStateOf("")
    var isUsernameValid: MutableState<Boolean> = mutableStateOf(false)
    var usernameErrMsg: MutableState<String> = mutableStateOf("")

    //EMAIL
    var email: MutableState<String> = mutableStateOf("")
    var isEmailValid: MutableState<Boolean> = mutableStateOf(false)
    var emailErrMsg: MutableState<String> = mutableStateOf("")

    //PASSWORD
    var password: MutableState<String> = mutableStateOf("")
    var isPasswordValid: MutableState<Boolean> = mutableStateOf(false)
    var passwordErrMsg: MutableState<String> = mutableStateOf("")

    //BUTTON
    var isEnabledLoginButton = false

    //CONFIRMAR CONTRASEÑA
    var confirmPassword: MutableState<String> = mutableStateOf("")
    var isConfirmPasswordValid: MutableState<Boolean> = mutableStateOf(false)
    var confirmPasswordErrMsg: MutableState<String> = mutableStateOf("")


    fun enabledLoginButton() {
        isEnabledLoginButton =
            isEmailValid.value &&
            isPasswordValid.value &&
            isUsernameValid.value &&
            isConfirmPasswordValid.value
    }


    fun validateConfirmPassword() {

        if (password.value == confirmPassword.value) {
            isConfirmPasswordValid.value = true
            confirmPasswordErrMsg.value = ""
        } else {
            isConfirmPasswordValid.value = false
            confirmPasswordErrMsg.value = "Las contraseñas no coinciden"
        }

        enabledLoginButton()
    }

    fun validateUsername() {

        if (username.value.length >= 5) {
            isUsernameValid.value = true
            usernameErrMsg.value = ""
        } else {
            isUsernameValid.value = false
            usernameErrMsg.value = "Al menos 5 caracteres"
        }

        enabledLoginButton()
    }


    fun validateEmail() {

        //ES UN EMAIL VALIDO
        if (Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {
            isEmailValid.value = true
            emailErrMsg.value = ""
        } else {
            isEmailValid.value = false
            emailErrMsg.value = "El email no es valido"
        }

        enabledLoginButton()
    }


    fun validatePassword() {
        if (password.value.length >= 6) {
            isPasswordValid.value = true
            passwordErrMsg.value = ""
        } else {
            isPasswordValid.value = false
            passwordErrMsg.value = "Al menos 6 caracteres"
        }

        enabledLoginButton()
    }

}