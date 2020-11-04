package com.example.coffeebean.orders

import android.opengl.Visibility
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
import com.example.coffeebean.database.CoffeeDatabase
import com.example.coffeebean.databinding.FragmentOrdersBinding
import com.example.coffeebean.orders.list.OrdersAdapter

/**
 * A simple [Fragment] subclass.
 */
class OrdersFragment : Fragment() {

    private lateinit var binding: FragmentOrdersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val args: OrdersFragmentArgs by navArgs()
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_orders, container, false)

        val application = requireNotNull(activity).application

        val coffeeDatabaseDao = CoffeeDatabase.getInstance(application).coffeeDatabaseDao

        val ordersFactory = OrdersViewModelFactory(args.userId, coffeeDatabaseDao, application)

        val orderViewModel = ViewModelProvider(this, ordersFactory).get(OrdersViewModel::class.java)

        val adapter = OrdersAdapter()

        binding.apply {
            ordersList.adapter = adapter
        }

        orderViewModel.ordersList.observe(viewLifecycleOwner, { orders ->
            adapter.createList(orders)
        })

        orderViewModel.showRecycler.observe(viewLifecycleOwner, { containsSomething ->
            when (containsSomething){
                1 -> {
                    binding.noItems.visibility = View.VISIBLE
                    orderViewModel.reset()
                }

                2 -> {
                    binding.noItems.visibility = View.GONE
                    orderViewModel.reset()
                }
            }

        })

        return binding.root
    }

}