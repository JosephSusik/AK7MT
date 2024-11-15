package com.example.trackee.network

data class InvoiceResponse(
    val id: String,
    val invoiceNumber: String,
    val customer: Customer,
    val items: List<Item>,
    val total: Double,
    val paymentDueDate: String,
    val dateIssued: String,
    val paymentDate: String,
    val currency: String
)

data class Customer(
    val customerId: String
)

data class Item(
    val description: String,
    val quantity: Int,
    val unitType: String,
    val unitPrice: Double,
    val totalPrice: Double
)
