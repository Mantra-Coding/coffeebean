package com.example.coffeebean.coffee.success.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeebean.database.entities.Product
import com.example.coffeebean.databinding.OrderItemBinding

class OrderViewHolder private constructor(val binding: OrderItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Product){
        binding.product = item
        binding.executePendingBindings()
    }

    companion object{
        fun from(parent: ViewGroup): OrderViewHolder{
            val inflater = LayoutInflater.from(parent.context)
            val binding = OrderItemBinding.inflate(inflater, parent, false)
            return OrderViewHolder(binding)
        }
    }
}