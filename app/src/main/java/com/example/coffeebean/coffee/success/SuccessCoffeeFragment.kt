package com.example.coffeebean.coffee.success

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs

import com.example.coffeebean.R
import com.example.coffeebean.coffee.success.list.OrderAdapter
import com.example.coffeebean.database.CoffeeDatabase
import com.example.coffeebean.databinding.FragmentSuccessCoffeeBinding

/**
 * A simple [Fragment] subclass.
 */
class SuccessCoffeeFragment : Fragment() {

    private lateinit var binding: FragmentSuccessCoffeeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_success_coffee, container, false)

        val application = requireNotNull(activity).application

        val args: SuccessCoffeeFragmentArgs by navArgs()

        val coffeeDatabaseDao = CoffeeDatabase.getInstance(application).coffeeDatabaseDao

        val factory = SuccessViewModelFactory(coffeeDatabaseDao, application, args.userId, args.productsNumber)

        val successViewModel = ViewModelProvider(this, factory).get(SuccessViewModel::class.java)

        binding.lifecycleOwner = this

        val adapter = OrderAdapter()

        successViewModel.orderedList.observe(viewLifecycleOwner, Observer { order ->
            adapter.submitList(order.reversed())
            successViewModel.exeded()
        })

        binding.apply {
            orderList.adapter = adapter
        }

        return binding.root
    }

}