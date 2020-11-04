package com.example.coffeebean

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.coffeebean.database.entities.Product
import com.example.coffeebean.database.entities.User


/**
 * Extension Function of [ImageView], it load the cocktail image with [Glide]
 * and set a placeholder until the image is loaded
 * @param productPlaceholder the product form where we get the imageId
 */
@BindingAdapter("cocktailImage")
fun ImageView.setCocktailImage(productPlaceholder: ProductPlaceholder?){
    productPlaceholder?.let {
        Glide.with(context)
            .load(it.imageId)
            .fitCenter()
            .placeholder(R.drawable.ic_hourglass_full)
            .into(this)
    }
}

/**
 * Extension Function of [TextView], it set the price of the product
 * @param productPlaceholder the product form where we get the price
 */
@BindingAdapter("productPrice")
fun TextView.setProductPrice(productPlaceholder: ProductPlaceholder?){
    productPlaceholder?.let {
        var productPriceToString = it.price.toString() + " €"
        productPriceToString = productPriceToString.replace(".", ",")
        text = productPriceToString
    }
}

/**
 * Extension Function of [TextView], it set the amount of the products
 * when the value is updated
 * @param productPlaceholder the product where we update the amount
 */
@SuppressLint("SetTextI18n")
@BindingAdapter("amountText")
fun TextView.setProductAmount(productPlaceholder: ProductPlaceholder?){
    productPlaceholder?.let {
        val amountToString = it.amount.toString()
        text = amountToString
    }
}

/**
 * Extension Function of [TextView], it show the total price of the product ordered
 * @param product the product form where we get the price
 */
@BindingAdapter("orderProductPrice")
fun TextView.setOrderPrice(product: Product?){
    product?.let {
        var productToString = (it.price * it.amount).toString() + " €"
        productToString = productToString.replace(".", ",")
        text = productToString
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("getIntroUser")
fun TextView.getIntroUser(user: User?){
    user?.let {
        text = "Ciao ${user.name} ${user.surname}!"
    }
}
