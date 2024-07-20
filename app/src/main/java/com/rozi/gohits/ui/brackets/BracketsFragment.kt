package com.rozi.gohits.ui.brackets

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rozi.gohits.MainActivity
import com.rozi.gohits.databinding.FragmentBracketsBinding


class BracketsFragment : AppCompatActivity() {
    private lateinit var binding: FragmentBracketsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentBracketsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        intent = Intent(this@BracketsFragment, MainActivity::class.java)
        startActivity(intent)
    }
}