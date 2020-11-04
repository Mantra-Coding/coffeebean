package com.example.coffeebean.coffee.mantra

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.example.coffeebean.R
import com.example.coffeebean.databinding.FragmentMantraBinding

/**
 * A simple [Fragment] subclass.
 */
class MantraFragment : Fragment() {

    private lateinit var binding: FragmentMantraBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mantra, container, false)

        return binding.root
    }

}
