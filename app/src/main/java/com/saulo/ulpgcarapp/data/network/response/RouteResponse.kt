package com.saulo.ulpgcarapp.data.network.response

import com.google.gson.annotations.SerializedName

data class RouteResponse(@SerializedName("features") val features: List<Features>)
data class Features(@SerializedName("geometry") val geometry: Geometries)
data class Geometries(@SerializedName("coordinates") var coordinates: List<List<Double>>)
