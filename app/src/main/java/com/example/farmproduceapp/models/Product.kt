package com.example.farmproduceapp.models

data class Product(
    val imageUri: String = "", // Provide default values
    val name: String = "",
    val quantity: String = "",
    val price: String = "",
    val farmerName: String = "",
    val farmerLocation: String = "",
    val farmerPhone: String = "",
    val id: String = ""
) {
    // No-argument constructor required by Firebase
    constructor() : this("", "", "", "", "", "", "", "")
}

