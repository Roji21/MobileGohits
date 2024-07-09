package com.rozi.gohits

import com.rozi.gohits.LoginRequest
import com.rozi.gohits.LoginResponse
import com.rozi.gohits.RegisterRequest
import com.rozi.gohits.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
interface ApiService {
    @FormUrlEncoded
    @POST("api/login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<LoginResponse>
}