package com.rozi.gohits

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val button = findViewById<Button>(R.id.sublogin)

        button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        val myTextView = findViewById<TextView>(R.id.register)

        myTextView.setOnClickListener {
            // Code to execute when the TextView is clicked
            val intent = Intent(this, Regis::class.java)
            startActivity(intent)
            finish()
        }
    }
}