package com.example.foodhub.data

import retrofit2.http.GET

interface FoodApi {

    @GET("/food")
    suspend fun foodApi(): List<String>

}