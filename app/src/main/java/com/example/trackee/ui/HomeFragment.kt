package com.example.trackee.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trackee.R
import com.example.trackee.network.ApiService
import com.example.trackee.network.InvoiceResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.widget.Toast
import com.example.trackee.ui.adapters.InvoiceAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class HomeFragment : Fragment() {

    private lateinit var apiService: ApiService
    private lateinit var invoicesRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        invoicesRecyclerView = view.findViewById(R.id.invoicesRecyclerView)
        invoicesRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5264")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        fetchInvoices()

        return view
    }

    private fun fetchInvoices() {
        apiService.getInvoices().enqueue(object : Callback<List<InvoiceResponse>> {
            override fun onResponse(
                call: Call<List<InvoiceResponse>>,
                response: Response<List<InvoiceResponse>>
            ) {
                if (response.isSuccessful) {
                    val invoices = response.body()
                    invoices?.let {
                        setupRecyclerView(it)
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Failed to load invoices: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<InvoiceResponse>>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun setupRecyclerView(invoices: List<InvoiceResponse>) {
        // Create the adapter with an onItemClick lambda
        val adapter = InvoiceAdapter(invoices) { invoiceNumber ->
            val fragment = InvoiceDetailFragment()
            val args = Bundle().apply {
                putString("invoiceNumber", invoiceNumber)
            }
            fragment.arguments = args

            // Navigate to InvoiceDetailFragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null) // Enables the back button
                .commit()
        }

        invoicesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        invoicesRecyclerView.adapter = adapter
    }


}
