package com.saulo.ulpgcarapp.domain.model

import com.google.gson.Gson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

data class Publish(
    var id: String = "",
    var origen: String = "",
    var destino: String = "",
    var municipio: String = "",
    var numeroPasajeros: Int = 1,
    var precioViaje: Int = 1,
    var paradas: List<String> = emptyList(),
    var pasajeros: MutableList<Passenger> = mutableListOf(),
    var valoracion: String = "",
    var estado: String = "",
    var fecha: String = "",
    var hora: String = "",
    var idUser: String = "",
    var user: User? = null,
    var image: String = ""
) {
fun toJson(): String = Gson().toJson(Publish(
    id, origen, destino, municipio, numeroPasajeros, precioViaje, paradas, pasajeros, valoracion, estado, fecha, hora,idUser,
    User(
        id = user?.id ?: "",
        username = user?.username ?: "",
        email = user?.email ?: "",
        image =
        if (!user?.image.isNullOrBlank())
            URLEncoder.encode(user?.image, StandardCharsets.UTF_8.toString())
        else "",
    ),
    if (image != "") URLEncoder.encode(image, StandardCharsets.UTF_8.toString()) else ""
))

    companion object {
        fun fromJson(data: String): Publish = Gson().fromJson(data, Publish::class.java)
    }

}