package com.saulo.ulpgcarapp.domain.use_cases.users

import com.saulo.ulpgcarapp.domain.repository.UsersRepository
import javax.inject.Inject

class GetUserById @Inject constructor(private val repository: UsersRepository) {

    operator fun invoke(id: String) = repository.getUSerById(id)

}