package com.example.coffeebean.coffee.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeebean.ProductPlaceholder
import com.example.coffeebean.databinding.ProductItemBinding
import com.example.coffeebean.setProductAmount

class ProductViewHolder private constructor(val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ProductPlaceholder) {
        binding.product = item

        binding.addButton.setOnClickListener {
            onAddProduct(item)
        }

        binding.removeButton.setOnClickListener {
            onRemoveProduct(item)
        }

        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ProductViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ProductItemBinding.inflate(inflater, parent, false)
            return ProductViewHolder(binding)
        }
    }

    private fun onAddProduct(item: ProductPlaceholder) {
        item.apply {
            amount = amount.plus(1)
            binding.productCount.setProductAmount(item)
        }
    }

    private fun onRemoveProduct(item: ProductPlaceholder) {
        item.apply {
            if (amount > 0) {
                amount = amount.minus(1)
                binding.productCount.setProductAmount(item)
            }
        }
    }
}