package com.rozi.gohits.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rozi.gohits.*
import com.rozi.gohits.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(), OnMenuItemClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var conAdapter: MyAdapter_content
    private var originalMenuContents: List<MenuHomeItem> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recyclerView: RecyclerView = binding.rvCategories
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val menuItems = listOf(
            MenuItem("All"),
            MenuItem("Badminton"),
            MenuItem("FootBall"),
            MenuItem("Pimpong"),
            MenuItem("Running"),
            MenuItem("Esport")
        )

        val adapter = MyAdapter(menuItems, this)
        recyclerView.adapter = adapter

        val conrecyclerView: RecyclerView = binding.conten
        conrecyclerView.layoutManager = GridLayoutManager(context, 2)

        fetchMenuHomeData()

        val userSession = getUserSession()
        if (userSession != null) {
            val (userId, usernama) = userSession
            binding.nama.text = usernama
        } else {
            Toast.makeText(context, "User session not found", Toast.LENGTH_SHORT).show()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMenuItemClick(menuItem: MenuItem) {
        conAdapter.filterData(menuItem.nama)
    }

    private fun getUserSession(): Pair<String, String>? {
        val sharedPreferences = activity?.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        val userId = sharedPreferences?.getString("userId", null)
        val usernama = sharedPreferences?.getString("usernama", null)
        return if (userId != null && usernama != null) {
            Pair(userId, usernama)
        } else {
            null
        }
    }

    private fun fetchMenuHomeData() {
        val apiService = ApiClient.getClient(requireContext()).create(ApiService::class.java)
        apiService.home().enqueue(object : Callback<MenuHomeResponse> {
            override fun onResponse(call: Call<MenuHomeResponse>, response: Response<MenuHomeResponse>) {
                if (response.isSuccessful) {
                    val menuHomeResponse = response.body()
                    if (menuHomeResponse != null) {
                        originalMenuContents = menuHomeResponse.data.map {
                            MenuHomeItem(it.title, it.upload, it.price, it.organizer, it.type_sport)
                        }
                        conAdapter = MyAdapter_content(originalMenuContents)
                        binding.conten.adapter = conAdapter
                    } else {
                        Toast.makeText(context, "Data tidak ditemukan", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Gagal mendapatkan data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MenuHomeResponse>, t: Throwable) {
                Toast.makeText(context, "Koneksi gagal: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
