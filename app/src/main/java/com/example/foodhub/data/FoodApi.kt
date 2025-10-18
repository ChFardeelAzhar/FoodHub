package com.example.foodhub.data

import com.example.foodhub.data.models.AuthResponse
import com.example.foodhub.data.models.SignInRequest
import com.example.foodhub.data.models.SignUpRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface FoodApi {

    @GET("/food")
    suspend fun foodApi(): List<String>

    /// request (ham phly bakend ko aik request martay hain)
    /// response (usse hamain response me kuch ata ha jo ham aik data class bana ke api me return karwatay hain)
    /// endPoint () (Ye btata ha ke request marni kaha ha server pe)

    @POST("/auth/signup")
    suspend fun signUp(@Body request: SignUpRequest): AuthResponse

    @POST("/auth/login")
    suspend fun signIn(@Body request: SignInRequest): AuthResponse

}