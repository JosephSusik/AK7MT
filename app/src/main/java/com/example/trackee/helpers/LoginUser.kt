package com.example.trackee.helpers

import android.content.Intent
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

class LoginManager(private val apiService: ApiService, private val activity: MainActivity) {

    fun loginUser(email: String, password: String) {
        val loginRequest = LoginRequest(email, password)

        apiService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()

                    if (loginResponse != null) {
                        val token = loginResponse.token
                        val name = loginResponse.name
                        val surname = loginResponse.surname
                        val email2 = loginResponse.email
                        val id = loginResponse.id

                        Log.d("Login", "Token: $token")
                        Log.d("Login", "Name: $name $surname")
                        Log.d("Login", "Email: $email2")
                        Log.d("Login", "ID: $id")

                        // Pass the token and user details to the next screen
                        val intent = Intent(activity, HomeScreenActivity::class.java)
                        intent.putExtra("token", token)
                        intent.putExtra("name", name)
                        intent.putExtra("surname", surname)
                        intent.putExtra("email", email2)
                        intent.putExtra("id", id)
                        activity.startActivity(intent)
                    }
                } else {
                    Toast.makeText(
                        activity,
                        "Login failed: ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(activity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("Login", "Error: ${t.message}")
            }
        })
    }
}
