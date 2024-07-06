package com.rozi.gohits.ui.home

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
import com.rozi.gohits.MenuItem
import com.rozi.gohits.Menuconten
import com.rozi.gohits.MyAdapter
import com.rozi.gohits.MyAdapter_content
import com.rozi.gohits.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recyclerView: RecyclerView = binding.rvCategories
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val menuItems = listOf(
            MenuItem("Menu 1"),
            MenuItem("Menu 2"),
            MenuItem("Menu 3"),
            MenuItem("Menu 1"),
            MenuItem("Menu 2"),
            MenuItem("Menu 3"),
            MenuItem("Menu 1"),
            MenuItem("Menu 2"),
            MenuItem("Menu 3")
        )

        val adapter = MyAdapter(menuItems)
        recyclerView.adapter = adapter

        val conrecyclerView: RecyclerView = binding.conten
        conrecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        conrecyclerView.layoutManager = GridLayoutManager(context, 2)

        val Menuconten = listOf(
            Menuconten("wtf_2022_website_1280x680px_1","a","a","a"),
            Menuconten("wtf_2022_website_1280x680px_1","a","a","a"),
            Menuconten("wtf_2022_website_1280x680px_1","a","a","a"),
            Menuconten("wtf_2022_website_1280x680px_1","a","a","a"),
            Menuconten("wtf_2022_website_1280x680px_1","a","a","a"),
            Menuconten("wtf_2022_website_1280x680px_1","a","a","a"),
            Menuconten("wtf_2022_website_1280x680px_1","a","a","a"),
            Menuconten("wtf_2022_website_1280x680px_1","a","a","a")
        )

        val conadapter = MyAdapter_content(Menuconten)
        conrecyclerView.adapter = conadapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}