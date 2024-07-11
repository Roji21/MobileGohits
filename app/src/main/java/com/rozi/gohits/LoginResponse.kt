package com.rozi.gohits

data class LoginResponse(
    val status: String,
    val message: String,
    val userid: String,
    val usernama: String,
    val userfoto: String
)