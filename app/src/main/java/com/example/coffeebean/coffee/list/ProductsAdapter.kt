package com.example.coffeebean.coffee.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.coffeebean.ProductPlaceholder

/**
 * [ListAdapter] for the main product list
 */
class ProductsAdapter():
    ListAdapter<ProductPlaceholder, ProductViewHolder>(ProductsDiffCallback()) {

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder.from(parent)
    }
}

class ProductsDiffCallback(): DiffUtil.ItemCallback<ProductPlaceholder>(){

    override fun areItemsTheSame(
        oldItem: ProductPlaceholder,
        newItem: ProductPlaceholder
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: ProductPlaceholder,
        newItem: ProductPlaceholder
    ): Boolean {
        return oldItem.name == newItem.name
    }

}