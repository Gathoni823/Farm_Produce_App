package com.example.farmproduceapp.data

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.farmproduceapp.models.Product
import com.example.farmproduceapp.navigation.ROUTE_VIEW_FARMER
import com.example.farmproduceapp.navigation.ROUTE_VIEW_PRODUCE
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.firestore


class ProductViewModel : ViewModel() {
    // LiveData for product details
    private val _product = MutableLiveData<Product?>()
    val product: LiveData<Product?> get() = _product

    // LiveData for loading state, error, and success messages
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _successMessage = MutableLiveData<String>()
    val successMessage: LiveData<String> get() = _successMessage

    // Initialize Firebase Realtime Database reference
    private val dbRef = FirebaseDatabase.getInstance().getReference("Produce") // Updated line

    // Function to show toast messages
    private fun showToast(message: String, context: Context) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    // Save product to Firebase Realtime Database
    fun saveProduct(
        name: String,
        quantity: String,
        price: String,
        imageUri: String,
        farmerName: String,
        farmerLocation: String,
        farmerPhone: String,
        id: String,
        navController: NavController,
        context: Context
    ) {
        val id = System.currentTimeMillis().toString()  // Use unique id
        val productData = Product(
            imageUri = imageUri,
            name = name,
            quantity = quantity,
            price = price,
            id = id,
            farmerName = farmerName,
            farmerLocation = farmerLocation,
            farmerPhone = farmerPhone
        )

        // Save the product to Realtime Database
        dbRef.child(id).setValue(productData).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                showToast("Product added successfully", context)
                navController.navigate(ROUTE_VIEW_PRODUCE)
            } else {
                showToast("Failed to add product", context)
            }
        }
    }

    // View all products from Firebase Realtime Database
    fun viewProducts(
        products: SnapshotStateList<Product>,
        context: Context
    ) {
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                products.clear()
                for (snap in snapshot.children) {
                    val value = snap.getValue(Product::class.java)
                    if (value != null) {
                        products.add(value)  // Add product to the list
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                showToast("Failed to fetch products", context)
            }
        })
    }

    // Update product in Firebase Realtime Database
    fun updateProduct(
        context: Context,
        navController: NavController,
        name: String,
        quantity: String,
        price: String,
        imageUri: String,
        farmerName: String,
        farmerLocation: String,
        farmerPhone: String,
        id: String
    ) {
        val updatedProduct = Product(
            imageUri = imageUri,
            name = name,
            quantity = quantity,
            price = price,
            farmerName = farmerName,
            farmerLocation = farmerLocation,
            farmerPhone = farmerPhone,
            id = id
        )

        // Update the product in Realtime Database
        dbRef.child(id).setValue(updatedProduct).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                showToast("Product updated successfully", context)
                navController.navigate(ROUTE_VIEW_PRODUCE)
            } else {
                showToast("Failed to update product", context)
            }
        }
    }

    // Delete product from Firebase Realtime Database
    fun deleteProduct(
        context: Context,
        id: String,
        navController: NavController
    ) {
        AlertDialog.Builder(context)
            .setTitle("Delete Product")
            .setMessage("Are you sure you want to delete this product?")
            .setPositiveButton("Yes") { _, _ ->
                dbRef.child(id).removeValue().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        showToast("Product deleted successfully", context)
                    } else {
                        showToast("Failed to delete product", context)
                    }
                }
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }


    fun fetchProductById(productId: String, context: Context) {
        val productRef = dbRef.child(productId) // Reference to the specific product by ID
        productRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("FetchProduct", "Snapshot: ${snapshot.value}")
                if (snapshot.exists()) {
                    val product = snapshot.getValue(Product::class.java)
                    _product.postValue(product) // Update LiveData
                } else {
                    Toast.makeText(context, "Product not found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FetchProduct", "DatabaseError: ${error.message}")
                Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    }



