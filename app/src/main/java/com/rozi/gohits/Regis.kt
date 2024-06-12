package com.rozi.gohits

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

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
    }
}