package com.example.trackee.ui

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.trackee.R
import com.example.trackee.network.ApiService
import android.content.Intent
import android.widget.Toast
import com.example.trackee.helpers.LoginManager
import com.example.trackee.network.LoginRequest
import com.example.trackee.network.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(),  LoginManager.LoginCallback {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    private lateinit var apiService: ApiService
    private lateinit var loginManager: LoginManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)  // Set your XML layout here

        // Initialize UI elements
        val emailEditText = findViewById<EditText>(R.id.et_email)
        val passwordEditText = findViewById<EditText>(R.id.et_password)
        val loginButton = findViewById<Button>(R.id.btn_login)

        // Initialize Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5264")
            //.baseUrl("http://localhost:5264")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
        loginManager = LoginManager(apiService)

        // Handle login button click
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginManager.loginUser(email, password, this)
            } else {
                Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Implementing onSuccess and onFailure methods from LoginCallback interface
    override fun onSuccess(token: String, name: String, surname: String, email: String, id: String) {
        // On successful login, navigate to HomeScreenActivity
        Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, HomeScreenActivity::class.java).apply {
            putExtra("token", token)
            putExtra("name", name)
            putExtra("surname", surname)
            putExtra("email", email)
            putExtra("id", id)
        }
        startActivity(intent)
        finish() // Close MainActivity to prevent navigating back to the login screen
    }

    override fun onFailure(errorMessage: String) {
        // Display the error message as a Toast
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        Log.e("Login", "Failure: $errorMessage")
    }
}
