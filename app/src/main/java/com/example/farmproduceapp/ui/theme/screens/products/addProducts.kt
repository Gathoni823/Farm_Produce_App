package com.example.farmproduceapp.ui.theme.screens.products

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.farmproduceapp.R
import com.example.farmproduceapp.data.ProductViewModel
import com.example.farmproduceapp.models.Product

@Composable
fun AddProductScreen(
    navController: NavController,
    productViewModel: ProductViewModel = viewModel() // Initialize the ViewModel
) {
    val imageUri = rememberSaveable { mutableStateOf<Uri?>(null) }
    val painter = rememberImagePainter(
        data = imageUri.value ?: R.drawable.logo,
        builder = { crossfade(true) }
    )
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { imageUri.value = it }
    }
    val context = LocalContext.current

    var productName by remember { mutableStateOf("") }
    var productQuantity by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var farmerName by remember { mutableStateOf("") }
    var farmerLocation by remember { mutableStateOf("") }
    var farmerPhone by remember { mutableStateOf("") }

    val products = remember { mutableStateListOf<Product>() }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(Icons.Filled.Home, contentDescription = "Home")
                    }
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(Icons.Filled.Settings, contentDescription = "Settings")
                    }
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(Icons.Filled.Email, contentDescription = "Email")
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { /* TODO */ },
                        containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                    ) {
                        Icon(Icons.Filled.AccountCircle, contentDescription = "Profile")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text(
                text = "ENTER NEW PRODUCT",
                fontSize = 25.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .background(Color.White)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { navController.popBackStack() }) {
                    Text(text = "BACK")
                }
                Button(onClick = {
                    val productId = System.currentTimeMillis().toString() // Generate a unique ID
                    val imageUriString = imageUri.value?.toString() ?: ""

                    val product = Product(
                        id = productId,
                        imageUri = imageUriString,
                        name = productName,
                        quantity = productQuantity,
                        price = productPrice,
                        farmerName = farmerName,
                        farmerLocation = farmerLocation,
                        farmerPhone = farmerPhone
                    )

                    products.add(product) // Save locally for this session

                    productViewModel.saveProduct(
                        id = productId,
                        imageUri = imageUriString,
                        name = productName,
                        quantity = productQuantity,
                        price = productPrice,
                        farmerName = farmerName,
                        farmerLocation = farmerLocation,
                        farmerPhone = farmerPhone,
                        navController = navController,
                        context = context
                    )
                }) {
                    Text(text = "SAVE")
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    shape = CircleShape,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(180.dp)
                ) {
                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier
                            .size(180.dp)
                            .clickable { launcher.launch("image/*") },
                        contentScale = ContentScale.Crop
                    )
                }
                Text(text = "Attach a picture ")

                // Use LazyColumn to display the input fields
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(10.dp)
                ) {
                    item {
                        TextField(
                            value = productName,
                            onValueChange = { productName = it },
                            label = { Text("Product Name") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item {
                        TextField(
                            value = productQuantity,
                            onValueChange = { productQuantity = it },
                            label = { Text("Quantity") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item {
                        TextField(
                            value = productPrice,
                            onValueChange = { productPrice = it },
                            label = { Text("Price") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item {
                        TextField(
                            value = farmerName,
                            onValueChange = { farmerName = it },
                            label = { Text("Farmer Name") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item {
                        TextField(
                            value = farmerLocation,
                            onValueChange = { farmerLocation = it },
                            label = { Text("Farmer Location") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item {
                        TextField(
                            value = farmerPhone,
                            onValueChange = { farmerPhone = it },
                            label = { Text("Farmer Phone") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(products) { product ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(text = "Name: ${product.name}")
                                    Text(text = "Quantity: ${product.quantity}")
                                    Text(text = "Price: ${product.price}")
                                    Text(text = "Farmer: ${product.farmerName}")
                                    Text(text = "Location: ${product.farmerLocation}")
                                    Text(text = "Phone: ${product.farmerPhone}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
