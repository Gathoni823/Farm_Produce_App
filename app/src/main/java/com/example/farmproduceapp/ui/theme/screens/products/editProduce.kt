package com.example.farmproduceapp.ui.theme.screens.products



import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.farmproduceapp.data.ProductViewModel


@Composable
fun UpdateProductScreen(
    navController: NavController,
    productId: String,
    productViewModel: ProductViewModel = viewModel()
) {
    val context = LocalContext.current
    val product by productViewModel.product.observeAsState()

    var name by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf("") }
    var farmerName by remember { mutableStateOf("") }
    var farmerLocation by remember { mutableStateOf("") }
    var farmerPhone by remember { mutableStateOf("") }

    // Fetch product by its ID
    LaunchedEffect(productId) {
        productViewModel.fetchProductById(productId, context)
    }

    // Observe the LiveData for the fetched product
    product?.let {
        name = it.name
        quantity = it.quantity
        price = it.price
        imageUri = it.imageUri
        farmerName = it.farmerName
        farmerLocation = it.farmerLocation
        farmerPhone = it.farmerPhone
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Update Product",
            fontSize = 30.sp,
            fontFamily = FontFamily.SansSerif,
            color = Color.Black,
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Product Name") },
            modifier = Modifier.fillMaxWidth(),
            enabled = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = quantity,
            onValueChange = { quantity = it },
            label = { Text("Quantity") },
            modifier = Modifier.fillMaxWidth(),
            enabled = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Price") },
            modifier = Modifier.fillMaxWidth(),
            enabled = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = imageUri,
            onValueChange = { imageUri = it },
            label = { Text("Image URI") },
            modifier = Modifier.fillMaxWidth(),
            enabled = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = farmerName,
            onValueChange = { farmerName = it },
            label = { Text("Farmer Name") },
            modifier = Modifier.fillMaxWidth(),
            enabled = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = farmerLocation,
            onValueChange = { farmerLocation = it },
            label = { Text("Farmer Location") },
            modifier = Modifier.fillMaxWidth(),
            enabled = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = farmerPhone,
            onValueChange = { farmerPhone = it },
            label = { Text("Farmer Phone") },
            modifier = Modifier.fillMaxWidth(),
            enabled = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                productViewModel.updateProduct(
                    context,
                    navController,
                    name,
                    quantity,
                    price,
                    imageUri,
                    farmerName,
                    farmerLocation,
                    farmerPhone,
                    productId
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Update Product")
        }
    }
}
