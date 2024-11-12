package com.example.trackee.network

data class LoginResponse(
    val token: String,
    val name: String,
    val surname: String,
    val email: String,
    val id: String
)