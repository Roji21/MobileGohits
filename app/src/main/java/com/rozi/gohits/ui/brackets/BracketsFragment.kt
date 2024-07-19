package com.rozi.gohits.ui.brackets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.rozi.gohits.databinding.FragmentBracketsBinding



class BracketsFragment : Fragment() {

    private var _binding: FragmentBracketsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bracketViewModel =
            ViewModelProvider(this).get(BracketViewModel::class.java)

        _binding = FragmentBracketsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}