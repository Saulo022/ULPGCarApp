package com.saulo.ulpgcarapp.presentation.screens.profile_edit

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saulo.ulpgcarapp.domain.model.Response
import com.saulo.ulpgcarapp.domain.model.User
import com.saulo.ulpgcarapp.domain.use_cases.users.UsersUseCases
import com.saulo.ulpgcarapp.presentation.utils.ComposeFileProvider
import com.saulo.ulpgcarapp.presentation.utils.ResultingActivityHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val usersUseCases: UsersUseCases,
    @ApplicationContext private val context: Context
) : ViewModel() {

    //STATE FORM
    var state by mutableStateOf(ProfileEditState())
        private set

    //USERNAME
    var usernameErrMsg by mutableStateOf("")
        private set

    val data = savedStateHandle.get<String>("user")
    val user = User.fromJson(data!!)

    fun onUsernameInput(username: String) {
        state = state.copy(username = username)
    }

    //RESPONSE
    var updateResponse by mutableStateOf<Response<Boolean>?>(null)
        private set

    //IMAGE
    var imageUri by mutableStateOf("")

    val resultingActivityHandler = ResultingActivityHandler()

    init {
        state = state.copy(username = user.username)
    }

    fun pickImage() {
        viewModelScope.launch {
            val result = resultingActivityHandler.getContent("image/*")
            if (result != null) {
                imageUri = result.toString()
            }
        }
    }

    fun takePhoto() {
        viewModelScope.launch {
            val result = resultingActivityHandler.takePicturePreview()
            if (result != null) {
                imageUri = ComposeFileProvider.getPathFromBitmap(context, result)
            }
        }
    }

        fun onUpdate() {
            val myUser = User(
                id = user.id,
                username = state.username,
                image = ""
            )
            update(myUser)
        }

        fun update(user: User) {
            viewModelScope.launch {
                updateResponse = Response.Loading
                val result = usersUseCases.update(user)
                updateResponse = result
            }
        }

        fun validateUsername() {

            if (state.username.length >= 5) {
                usernameErrMsg = ""
            } else {
                usernameErrMsg = "Al menos 5 caracteres"
            }

        }

    }
