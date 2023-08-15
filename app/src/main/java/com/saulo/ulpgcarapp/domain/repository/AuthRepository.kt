package com.saulo.ulpgcarapp.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.saulo.ulpgcarapp.domain.model.Response

interface AuthRepository {

    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): Response<FirebaseUser>
}