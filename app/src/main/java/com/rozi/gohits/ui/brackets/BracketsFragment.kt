package com.rozi.gohits.ui.brackets

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rozi.gohits.ApiService
import com.rozi.gohits.Det
import com.rozi.gohits.MainActivity
import com.rozi.gohits.Menu_perserta
import com.rozi.gohits.MyAdapter_Detail
import com.rozi.gohits.databinding.FragmentBracketsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BracketsFragment : AppCompatActivity() {
    private lateinit var binding: FragmentBracketsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentBracketsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("id")
        val root: View = binding.root
        val recyclerView: RecyclerView = binding.rvParticipants
        recyclerView.layoutManager = LinearLayoutManager(this@BracketsFragment, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = GridLayoutManager(this@BracketsFragment, 2)
        val apiService = ApiClient.getClient(this).create(ApiService::class.java)
        val call = apiService.detail(id.toString())

        call.enqueue(object : Callback<Det> {
            override fun onResponse(call: Call<Det>, response: Response<Det>) {
                if (response.isSuccessful && response.body() != null) {
                    val det = response.body()
                    if (det != null) {
                        val menu_perserta = det.perserta.map {
                            Menu_perserta(it.nama)
                        }

                        // Log untuk jumlah peserta
                        Log.d("BracketsFragment", "Number of participants: ${menu_perserta.size}")

                        // Atur adapter
                        val conadapter = MyAdapter_Detail(menu_perserta)
                        binding.rvParticipants.adapter = conadapter
                        det.data.map {
                            binding.judu.text = it.title
                            binding.date.text = it.date_column
                            binding.time.text = it.time
                            binding.location.text = it.location
                            binding.organizer.text = it.organizer
                            binding.parti.text = menu_perserta.size.toString()+"/"+it.participant
                            val baseUrl = "https://gohit.id/assets/image/"
                            val imageUrl = baseUrl + it.upload // Menggabungkan URL dasar dengan nama file gambar

                            // Gunakan Glide untuk memuat gambar
                            Glide.with(this@BracketsFragment)
                                .load(imageUrl)
                                .into(binding.imagebc)
                        }
                    } else {
                        Toast.makeText(this@BracketsFragment, "Data tidak ditemukan", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@BracketsFragment, "Failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Det>, t: Throwable) {
                Toast.makeText(this@BracketsFragment, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        intent = Intent(this@BracketsFragment, MainActivity::class.java)
        startActivity(intent)
    }
}
