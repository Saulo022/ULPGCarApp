package com.saulo.ulpgcarapp.domain.model

data class Campus(
    var name: String = "",
    var longitude: String = "",
    var latitude: String = "",
    var faculties_colleges: MutableList<Faculty_College> = mutableListOf()
    )

data class Faculty_College(
    var name: String = "",
    var longitude: String = "",
    var latitude: String = "",
)
