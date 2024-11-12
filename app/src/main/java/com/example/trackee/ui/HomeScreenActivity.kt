package com.example.trackee.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.trackee.R

class HomeScreenActivity : AppCompatActivity() {

    private lateinit var helloTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        helloTextView = findViewById(R.id.tv_hello_world)

        // Retrieve data passed from the login activity
        val token = intent.getStringExtra("token")
        val name = intent.getStringExtra("name")
        val surname = intent.getStringExtra("surname")
        val email = intent.getStringExtra("email")
        val id = intent.getStringExtra("id")

        helloTextView.text = "Hello, $name $surname!\nYour email: $email\nToken: $token\nID: $id"
    }
}
