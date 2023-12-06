package com.saulo.ulpgcarapp.presentation.screens.chats

import com.saulo.ulpgcarapp.domain.model.Publish

data class ChatState (
    var rideId: String = "",
    var message: String = "",

    var origin: String = "",
    var destination: String = "",
    var date: String = "",
    var time: String = "",
    var participants: Int = 1,
    var image: String = ""
)