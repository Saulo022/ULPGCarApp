package com.saulo.ulpgcarapp.data.network

import com.saulo.ulpgcarapp.data.network.response.Matrix
import com.saulo.ulpgcarapp.data.network.response.MatrixResponse
import com.saulo.ulpgcarapp.data.network.response.RouteResponse
import com.saulo.ulpgcarapp.data.network.response.SearchResponse
import retrofit2.Response
import retrofit2.http.*

interface RouteApiClient {

    @GET("/geocode/search")
    suspend fun search(
        @Query("api_key") apiKey: String,
        @Query("text") text: String,
        @Query("boundary.circle.lon", encoded = true) long: String,
        @Query("boundary.circle.lat", encoded = true) lat: String,
        @Query("boundary.circle.radius") radius: Int,
        @Query("boundary.country") country: String
    ) : Response<SearchResponse>

    @GET("/v2/directions/driving-car")
    suspend fun getRoute(
        @Query("api_key") apiKey: String,
        @Query("start", encoded = true) start: String,
        @Query("end", encoded = true) end: String
    ): Response<RouteResponse>

    @Headers("Authorization: 5b3ce3597851110001cf6248430006dcbe134f72aea0f41e3b68d35b")
    @POST("/v2/matrix/driving-car")
    suspend fun optimisedRoute(@Body matrix: Matrix) : Response<MatrixResponse>
}