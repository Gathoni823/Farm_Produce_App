package com.example.farmproduceapp.models

class Users {
    data class SignupModel(
        var firstName: String = "",
        var lastName: String = "",
        var email: String = "",
        var password: String = "",
        var phoneNumber: String = "",
        var confirmPassword: String = "",
        var userType: String = "",
        var userId: String = "",
        val userName: String = "" // Make sure this has a default value

    )
}