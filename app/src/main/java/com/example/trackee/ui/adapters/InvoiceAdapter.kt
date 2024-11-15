package com.example.trackee.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trackee.R
import com.example.trackee.network.InvoiceResponse
import formatDate

class InvoiceAdapter(
    private val invoices: List<InvoiceResponse>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder>() {

    inner class InvoiceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val invoiceNumberTextView: TextView = view.findViewById(R.id.invoiceNumberTextView)
        val totalTextView: TextView = view.findViewById(R.id.totalTextView)
        val dateIssuedTextView: TextView = view.findViewById(R.id.dateIssuedTextView)

        init {
            view.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val invoice = invoices[position]
                    onItemClick(invoice.invoiceNumber) // Pass invoice number on click
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvoiceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_invoice, parent, false)
        return InvoiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: InvoiceViewHolder, position: Int) {
        val invoice = invoices[position]
        holder.invoiceNumberTextView.text = invoice.invoiceNumber
        holder.totalTextView.text = "${invoice.currency} ${invoice.total}"
        holder.dateIssuedTextView.text = "Issued: ${formatDate(invoice.dateIssued)}"
    }

    override fun getItemCount(): Int = invoices.size
}
