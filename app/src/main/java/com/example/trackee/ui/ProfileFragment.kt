package com.example.trackee.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.trackee.R
import com.example.trackee.helpers.LoginManager
import com.example.trackee.network.ApiService

class ProfileFragment : Fragment() {

    private lateinit var loginManager: LoginManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Access SharedPreferences
        val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        // Retrieve user details
        val name = sharedPreferences.getString("name", "N/A")
        val surname = sharedPreferences.getString("surname", "N/A")
        val email = sharedPreferences.getString("email", "N/A")

        // Find and set data on TextViews
        val nameTextView: TextView = view.findViewById(R.id.user_name)
        val surnameTextView: TextView = view.findViewById(R.id.user_surname)
        val emailTextView: TextView = view.findViewById(R.id.user_email)

        nameTextView.text = "Name: $name"
        surnameTextView.text = "Surname: $surname"
        emailTextView.text = "Email: $email"
        // Initialize LoginManager
        loginManager = LoginManager(null, requireContext())

        // Logout button
        val logoutButton: Button = view.findViewById(R.id.btn_logout)
        logoutButton.setOnClickListener {
            loginManager.logoutUser()
        }

        return view
    }
}
