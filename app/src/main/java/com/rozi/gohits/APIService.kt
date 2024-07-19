package com.rozi.gohits

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface ApiService {
    @FormUrlEncoded
    @POST("api/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>
    @FormUrlEncoded
    @POST("api/register")
    fun register(
        @Field("nama") nama: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String
    ): Call<RegisterResponse>


    @GET("api/home")
    fun home(): Call<MenuHomeResponse>
    @GET("api/home")
    fun dash(): Call<MenuDasResponse>
    @Multipart
    @POST("api/addevent")
    fun uploadImage(
        @Part image: MultipartBody.Part,
        @Part("iduser") iduser: RequestBody,
        @Part("title") title: RequestBody,
        @Part("date") date: RequestBody,
        @Part("time") time: RequestBody,
        @Part("price") price: RequestBody,
        @Part("organizer") organizer: RequestBody,
        @Part("location") location: RequestBody,
        @Part("category") category: RequestBody,
        @Part("participant") participant: RequestBody
    ): Call<UploadResponse>
}
