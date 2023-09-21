package com.saulo.ulpgcarapp.data.network.response

data class SearchResponse( val features: List<Feature>)
data class Feature( val geometry: Geometry, val properties: Properties)
data class Geometry( val coordinates: List<Double>)
data class Properties( val label: String)