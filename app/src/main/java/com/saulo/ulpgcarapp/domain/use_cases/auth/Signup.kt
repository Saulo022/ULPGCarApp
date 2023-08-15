package com.saulo.ulpgcarapp.domain.use_cases.auth

import com.saulo.ulpgcarapp.domain.model.User
import com.saulo.ulpgcarapp.domain.repository.AuthRepository
import javax.inject.Inject

class Signup @Inject constructor(private val  repository: AuthRepository) {

    suspend operator fun invoke(user: User) = repository.signUp(user)
}