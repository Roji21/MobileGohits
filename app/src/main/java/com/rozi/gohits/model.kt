package com.rozi.gohits

data class LoginRequest(
    val email: String,
    val password: String
)


data class RegisterRequest(
    val username: String,
    val password: String,
    val email: String
)

data class RegisterResponse(
    val message: String
)