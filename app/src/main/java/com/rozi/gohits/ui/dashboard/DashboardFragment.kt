package com.rozi.gohits.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rozi.gohits.Menuconten
import com.rozi.gohits.Menudashboard
import com.rozi.gohits.MyAdapter_content
import com.rozi.gohits.MyAdapter_dashboard
import com.rozi.gohits.databinding.FragmentDashboardBinding

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

//        val textView: TextView = binding.textDashboard
//        dashboardViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        val conrecyclerView: RecyclerView = binding.Dashboard
        conrecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val Menudashboard = listOf(
            Menudashboard("wtf_2022_website_1280x680px_1","a112","a","a","a","a"),
            Menudashboard("wtf_2022_website_1280x680px_1","a1","a","a","a","a"),
            Menudashboard("wtf_2022_website_1280x680px_1","a1","a","a","a","a"),
            Menudashboard("wtf_2022_website_1280x680px_1","a1","a","a","a","a"),
            Menudashboard("wtf_2022_website_1280x680px_1","a1","a","a","a","a"),
            Menudashboard("wtf_2022_website_1280x680px_1","a1","a","a","a","a"),
            Menudashboard("wtf_2022_website_1280x680px_1","a1","a","a","a","a")
        )

        val conadapter = MyAdapter_dashboard(Menudashboard)
        conrecyclerView.adapter = conadapter
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}