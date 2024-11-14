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

class LoginManager(private val apiService: ApiService?, private val context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun loginUser(email: String, password: String) {
        val loginRequest = LoginRequest(email, password)

        apiService?.login(loginRequest)?.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()

                    if (loginResponse != null) {

                        // Store user data in SharedPreferences
                        sharedPreferences.edit().apply {
                            putString("token", loginResponse.token)
                            putString("name", loginResponse.name)
                            putString("surname", loginResponse.surname)
                            putString("email", loginResponse.email)
                            putString("id", loginResponse.id)
                            apply()
                        }

                        // Navigate to HomeScreenActivity
                        val intent = Intent(context, HomeScreenActivity::class.java)
                        context.startActivity(intent)
                    } else {
                        Toast.makeText(
                            context,
                            "Login failed: ${response.message()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        context,
                        "Login failed: ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("Login", "Error: ${t.message}")
            }
        })
    }
    fun logoutUser() {
        // Clear user data on logout
        sharedPreferences.edit().clear().apply()
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }
}
