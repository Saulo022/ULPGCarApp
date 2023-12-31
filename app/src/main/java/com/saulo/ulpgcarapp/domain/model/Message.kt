package com.saulo.ulpgcarapp.domain.model

import java.sql.Timestamp

data class Message(
    var userId: String = "",
    val contenido: String = "",
    val fecha: String = "",
    val hora: String = "",
    val horaSimple: String = "",
    var docuId: String = "",
    var imagen: String = "",
    var name: String = ""
)

data class UserDto(val name: String, val id: String)