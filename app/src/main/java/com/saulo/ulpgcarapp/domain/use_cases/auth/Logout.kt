package com.saulo.ulpgcarapp.domain.use_cases.auth

import com.saulo.ulpgcarapp.domain.repository.AuthRepository
import javax.inject.Inject

class Logout @Inject constructor(private val repository: AuthRepository) {

    operator fun invoke() = repository.logout()
}