package com.example.foodhub.data.models

data class SignUpRequest(
    val name: String,
    val email: String,
    val password: String,
)

data class SignInRequest(
    val email: String,
    val password: String,
)
