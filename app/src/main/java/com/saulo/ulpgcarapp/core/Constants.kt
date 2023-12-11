package com.saulo.ulpgcarapp.core

import com.saulo.ulpgcarapp.domain.model.Campus
import com.saulo.ulpgcarapp.domain.model.Faculty_College

object Constants {

    const val USERS = "Users"
    const val PUBLISH = "Publish"
    const val CHAT = "Chat"


    const val API_KEY = "5b3ce3597851110001cf6248430006dcbe134f72aea0f41e3b68d35b"

    //Latitude and longitude coordinates of the city of Las Palmas de Gran Canaria.
    const val LATITUDE = "28.0997300"
    const val LONGITUDE = "-15.4134300"

    const val RADIUS = 50

    const val COUNTRY = "ES"

    //list of municipalities in Las Palmas
    val municipalities  = listOf(
        "Agaete",
        "Agüimes",
        "Artenara",
        "Arucas",
        "Firgas",
        "Gáldar",
        "Ingenio",
        "Las Palmas",
        "Mogán",
        "Moya",
        "San Bartolomé de Tirajana",
        "La Aldea de San Nicolás",
        "Santa Brígida",
        "Santa Lucía",
        "Santa María de Guía",
        "Vega de San Mateo",
        "Tejeda",
        "Telde",
        "Teror",
        "Valleseco",
        "Valsequillo"
    )

    val facultiesCollegesOfTafira: MutableList<Faculty_College> = mutableListOf(
        Faculty_College(name = "Escuela de Arquitectura", longitude = "-15.453853294616849", latitude = "28.072779650727743"),
        Faculty_College(name = "Escuela de Ingeniería de Telecomunicación y Electrónica", longitude = "-15.453920211318598", latitude = "28.07162418905613"),
        Faculty_College(name = "Facultad de Ciencias Jurídicas", longitude = "-15.44926385362118", latitude = "28.07953806299269")
    )


    val campuses: MutableList<Campus> = mutableListOf(
        Campus(name = "Campus de Tafira", longitude = "-15.450042679042715", latitude = "28.077989738893347", faculties_colleges = facultiesCollegesOfTafira),
        Campus(name = "Campus Universitario del Obelisco", longitude = "-15.42465211153699", latitude = "28.112021289365412")
    )


}