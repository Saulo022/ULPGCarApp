package com.saulo.ulpgcarapp.domain.model

data class Publish(
    var id: String = "",
    var origen: String = "",
    var destino: String = "",
    var municipio: String = "",
    var numeroPasajeros: Int = 1,
    var paradas: List<String> = emptyList(),
    var pasajeros: List<String> = emptyList(),
    var precio: String = "",
    var valoracion: String = "",
    var estado: String = "",
    var fecha: String = "",
    var hora: String = "",
    var idUser: String = ""
)
