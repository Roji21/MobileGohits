package com.rozi.gohits

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Regis : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regis)

        val myTextView = findViewById<TextView>(R.id.login)

        myTextView.setOnClickListener {
            // Code to execute when the TextView is clicked
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
        val regis = findViewById<Button>(R.id.regis)
        val usernameEditText = findViewById<EditText>(R.id.username)
        val emailEditText = findViewById<EditText>(R.id.email)
        val passwordEditText = findViewById<EditText>(R.id.pass1)
        val password2EditText = findViewById<EditText>(R.id.pass2)

        regis.setOnClickListener {
            val nama = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val repassword = password2EditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()

            if (nama.isEmpty() || password.isEmpty()||email.isEmpty()||repassword.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val apiService = ApiClient.instance.create(ApiService::class.java)
            val call = apiService.register(nama,email, password,repassword)
            call.enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                    Log.d("MainActivity", "Response: $response")
                    if (response.isSuccessful && response.body() != null) {
                        Log.d("MainActivity", "Response body: ${response.body()}")
                        if (response.body()!!.status == "success") {
                            Toast.makeText(this@Regis, response.body()!!.message, Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@Regis, response.body()!!.message, Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@Regis, "Login Failed", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    Toast.makeText(this@Regis, t.message, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}