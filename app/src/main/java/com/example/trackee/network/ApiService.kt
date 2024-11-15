package com.example.trackee.network
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    // Define the login endpoint
    @POST("/api/Users/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    // Define the invoices endpoint
    @GET("/api/Invoices")
    fun getInvoices(): Call<List<InvoiceResponse>>

    @GET("/api/Invoices/{invoiceNumber}")
    fun getInvoiceDetails(@Path("invoiceNumber") invoiceNumber: String): Call<InvoiceResponse>
}
