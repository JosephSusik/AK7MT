package com.example.trackee.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.trackee.R
import com.example.trackee.network.ApiService
import com.example.trackee.network.InvoiceResponse
import formatDate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InvoiceDetailFragment : Fragment() {

    private lateinit var apiService: ApiService
    private lateinit var invoiceNumber: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the invoice number passed as an argument
        invoiceNumber = arguments?.getString("invoiceNumber") ?: ""

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5264")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_invoice_detail, container, false)
        fetchInvoiceDetails(view)
        return view
    }

    private fun fetchInvoiceDetails(view: View) {
        apiService.getInvoiceDetails(invoiceNumber).enqueue(object : Callback<InvoiceResponse> {
            override fun onResponse(
                call: Call<InvoiceResponse>,
                response: Response<InvoiceResponse>
            ) {
                if (response.isSuccessful) {
                    val invoice = response.body()
                    invoice?.let {
                        displayInvoiceDetails(view, it)
                    }
                } else {
                    // Handle error
                }
            }

            override fun onFailure(call: Call<InvoiceResponse>, t: Throwable) {
                // Handle failure
            }
        })
    }

    private fun displayInvoiceDetails(view: View, invoice: InvoiceResponse) {
        // Update the views with invoice details
        view.findViewById<TextView>(R.id.invoiceNumberTextView).text = invoice.invoiceNumber
        view.findViewById<TextView>(R.id.totalTextView).text = "${invoice.currency} ${invoice.total}"
        view.findViewById<TextView>(R.id.dateIssuedTextView).text = "Issued: ${formatDate(invoice.dateIssued)}"
        view.findViewById<TextView>(R.id.paymentDueDateTextView).text = "Due: ${formatDate(invoice.paymentDueDate)}"
    }

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.supportActionBar?.apply {
            title = "Invoice Details"
            setDisplayHomeAsUpEnabled(true) // Show back arrow
        }
    }
}
