package com.example.trackee.helpers

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import com.example.trackee.network.ApiService
import com.example.trackee.network.LoginRequest
import com.example.trackee.network.LoginResponse
import com.example.trackee.ui.HomeScreenActivity
import com.example.trackee.ui.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginManager(private val apiService: ApiService) {

    // Define LoginCallback interface to handle success and failure responses
    interface LoginCallback {
        fun onSuccess(token: String, name: String, surname: String, email: String, id: String)
        fun onFailure(errorMessage: String)
    }

    fun loginUser(email: String, password: String, callback: LoginCallback) {
        val loginRequest = LoginRequest(email, password)

        apiService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()

                    if (loginResponse != null) {
                        // Invoke the success callback
                        callback.onSuccess(
                            loginResponse.token,
                            loginResponse.name,
                            loginResponse.surname,
                            loginResponse.email,
                            loginResponse.id
                        )
                    } else {
                        callback.onFailure("Login response is null")
                    }
                } else {
                    callback.onFailure("Login failed: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                callback.onFailure("Error: ${t.message}")
                Log.e("Login", "Error: ${t.message}")
            }
        })
    }
    
}
