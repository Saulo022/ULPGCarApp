package com.saulo.ulpgcarapp.domain.use_cases.auth

data class AuthUseCases(
    val getCurrentUSer: GetCurrentUser,
    val login: Login,
    val logout: Logout
)