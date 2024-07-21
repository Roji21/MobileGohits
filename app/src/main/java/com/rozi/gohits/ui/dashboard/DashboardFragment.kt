package com.rozi.gohits.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rozi.gohits.ApiService
import com.rozi.gohits.MenuDasResponse
import com.rozi.gohits.Menudashboard
import com.rozi.gohits.MyAdapter_dashboard
import com.rozi.gohits.databinding.FragmentDashboardBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val userSession = getUserSession()
        if (userSession != null) {
            val (userId, usernama) = userSession
            binding.textView4.text = usernama
        } else {
            Toast.makeText(context, "User session not found", Toast.LENGTH_SHORT).show()
        }
        val conrecyclerView: RecyclerView = binding.Dashboard
        conrecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        fetchMenuHomeData()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun fetchMenuHomeData() {
        val apiService = ApiClient.getClient(requireContext()).create(ApiService::class.java)
        apiService.dash().enqueue(object : Callback<MenuDasResponse> {
            override fun onResponse(call: Call<MenuDasResponse>, response: Response<MenuDasResponse>) {
                if (response.isSuccessful) {
                    val menuHomeResponse = response.body()
                    if (menuHomeResponse != null) {
                        val menudashboard = menuHomeResponse.data.map {
                            Menudashboard(it.upload, it.title, it.organizer, it.participant, it.location, it.time)
                        }
                        val conadapter = MyAdapter_dashboard(menudashboard)
                        binding.Dashboard.adapter = conadapter
                    } else {
                        Toast.makeText(context, "Data tidak ditemukan", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Gagal mendapatkan data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MenuDasResponse>, t: Throwable) {
                Toast.makeText(context, "Koneksi gagal: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
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
}