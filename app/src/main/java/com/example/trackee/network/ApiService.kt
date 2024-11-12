package com.example.trackee.network
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    // Define the login endpoint
    @POST("/api/Users/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
}
