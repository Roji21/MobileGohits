package com.rozi.gohits

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Retrofit
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://gohit.id/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

//        val apiService = retrofit.create(APIService::class.java)

        val usernameEditText = findViewById<EditText>(R.id.username)
        val passwordEditText = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.sublogin)
        val regisButton = findViewById<TextView>(R.id.register)
        regisButton.setOnClickListener {
            val intent = Intent(this, Regis::class.java)
            startActivity(intent)
            finish()
        }

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val apiService = ApiClient.getClient(this).create(ApiService::class.java)
            val call = apiService.login(username, password)

            call.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    Log.d("MainActivity", "Response: $response")
                    if (response.isSuccessful && response.body() != null) {
                        Log.d("MainActivity", "Response body: ${response.body()}")
                        if (response.body()!!.status == "success") {
                            val userId = response.body()!!.userid
                            val usernama = response.body()!!.usernama
                            saveSession(userId, usernama)
                            val intent = Intent(this@Login, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@Login, response.body()!!.message, Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@Login, "Login Failed", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@Login, t.message, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
    private fun saveSession(userId: String, usernama: String) {
        val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("userId", userId)
        editor.putString("usernama", usernama)
        editor.putBoolean("isLoggedIn", true)
        editor.apply()
    }

}