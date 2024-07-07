package com.rozi.gohits

import com.rozi.gohits.LoginRequest
import com.rozi.gohits.LoginResponse
import com.rozi.gohits.RegisterRequest
import com.rozi.gohits.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface APIService {
    @POST("register")
    fun register(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

    @POST("auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
}