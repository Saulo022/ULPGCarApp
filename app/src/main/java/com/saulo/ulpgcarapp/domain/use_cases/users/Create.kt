package com.saulo.ulpgcarapp.domain.use_cases.users

import com.saulo.ulpgcarapp.domain.model.User
import com.saulo.ulpgcarapp.domain.repository.UsersRepository
import javax.inject.Inject

class Create @Inject constructor(private val repository: UsersRepository) {

    suspend operator fun invoke(user: User) = repository.create(user)

}