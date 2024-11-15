package com.example.trackee.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trackee.R
import com.example.trackee.network.InvoiceResponse
import formatDate

class InvoiceAdapter(private val invoices: List<InvoiceResponse>) :
    RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder>() {

    class InvoiceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val invoiceNumber: TextView = view.findViewById(R.id.invoiceNumberTextView)
        val total: TextView = view.findViewById(R.id.totalTextView)
        val dateIssued: TextView = view.findViewById(R.id.dateIssuedTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvoiceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_invoice, parent, false)
        return InvoiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: InvoiceViewHolder, position: Int) {
        val invoice = invoices[position]
        holder.invoiceNumber.text = invoice.invoiceNumber
        holder.total.text = "${invoice.currency} ${invoice.total}"
        holder.dateIssued.text = "Issued: ${formatDate(invoice.dateIssued)}"
    }

    override fun getItemCount(): Int = invoices.size
}