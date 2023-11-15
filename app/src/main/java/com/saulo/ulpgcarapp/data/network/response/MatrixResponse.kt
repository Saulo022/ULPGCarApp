package com.saulo.ulpgcarapp.data.network.response

data class MatrixResponse(
    val destinations: List<Destination>,
    val durations: List<List<Double>>,
    val metadata: Metadata,
    val sources: List<Source>
)

data class Destination(
    val location: List<Double>,
    val snapped_distance: Double
)

data class Source(
    val location: List<Double>,
    val snapped_distance: Double
)

data class Metadata(
    val attribution: String,
    val engine: Engine,
    val query: Query,
    val service: String,
    val timestamp: Long
)

data class Engine(
    val build_date: String,
    val graph_date: String,
    val version: String
)

data class Query(
    val locations: List<List<Double>>,
    val profile: String,
    val responseType: String
)

data class Matrix(val locations: List<List<Double>>)