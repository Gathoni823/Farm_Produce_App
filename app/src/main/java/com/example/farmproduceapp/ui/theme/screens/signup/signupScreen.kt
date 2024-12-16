package com.example.wemahostels.ui.theme.screens.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.farmproduceapp.models.Users
import com.example.farmproduceapp.navigation.ROUTE_LOGIN
import com.example.wemahostels.data.AuthViewModel


@Composable
fun SignupScreen(navController: NavController) {

    val context = LocalContext.current
    val authViewModel: AuthViewModel = viewModel()

    val isLoading by authViewModel.isLoading.collectAsState()

    // Initializing the SignupModel
    var signupModel by remember { mutableStateOf(Users.SignupModel()) }
    val userTypes = listOf("Client", "Farmer")

    var expanded by remember { mutableStateOf(false) } // For dropdown menu

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Register Here",
                fontSize = 20.sp,
                color = Color.Yellow,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(Color.Black)
                    .padding(20.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Input fields
            OutlinedTextField(
                value = signupModel.firstName,
                onValueChange = { signupModel = signupModel.copy(firstName = it) },
                label = { Text(text = "First Name") },
                placeholder = { Text(text = "Enter your first name") }
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = signupModel.lastName,
                onValueChange = { signupModel = signupModel.copy(lastName = it) },
                label = { Text(text = "Last Name") },
                placeholder = { Text(text = "Enter your last name") }
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = signupModel.phoneNumber,
                onValueChange = { signupModel = signupModel.copy(phoneNumber = it) },
                label = { Text(text = "Phone Number") },
                placeholder = { Text(text = "Enter your phone number") }
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = signupModel.email,
                onValueChange = { signupModel = signupModel.copy(email = it) },
                label = { Text(text = "Email") },
                placeholder = { Text(text = "Enter your email") }
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = signupModel.password,
                onValueChange = { signupModel = signupModel.copy(password = it) },
                label = { Text(text = "Password") },
                placeholder = { Text(text = "Enter your password") },
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = signupModel.confirmPassword,
                onValueChange = { signupModel = signupModel.copy(confirmPassword = it) },
                label = { Text(text = "Confirm Password") },
                placeholder = { Text(text = "Re-enter your password") },
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Dropdown for user type
            Box {
                OutlinedTextField(
                    value = signupModel.userType,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(text = "User Type") },
                    placeholder = { Text(text = "Select user type") },
                    trailingIcon = {
                        IconButton(onClick = { expanded = true }) {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    userTypes.forEach { type ->
                        DropdownMenuItem(
                            onClick = {
                                signupModel = signupModel.copy(userType = type)
                                expanded = false
                            },
                            text = { Text(text = type) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    authViewModel.signup(
                        firstName = signupModel.firstName,
                        lastName = signupModel.lastName,
                        phoneNumber = signupModel.phoneNumber,
                        email = signupModel.email,
                        password = signupModel.password,
                        confirmPassword = signupModel.confirmPassword,
                        userType = signupModel.userType,
                        navController = navController,
                        context = context
                    )
                },
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.Black, strokeWidth = 4.dp)
                } else {
                    Text(
                        modifier = Modifier.padding(10.dp),
                        color = Color.Green,
                        text = "Sign Up"
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            ClickableText(
                text = AnnotatedString("Already have an account? Log In Here"),
                onClick = { navController.navigate(ROUTE_LOGIN) },
                style = TextStyle(
                    color = Color.Blue,
                    fontSize = 16.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignupScreenPreview() {
    SignupScreen(rememberNavController())
}
