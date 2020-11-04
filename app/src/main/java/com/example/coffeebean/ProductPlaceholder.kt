package com.example.coffeebean

/**
 * [ProductPlaceholder] is the class that defines the elements of the list of products to be ordered,
 * in this case inserted in a "fake" way directly in the app
 */
data class ProductPlaceholder(
    /**
     * Name of the product
     */
    val name: String,
    /**
     * ingredients to make the product
     */
    val description: String,
    /**
     * Price of the product
     */
    val price: Double,
    /**
     * Id of the image of the product,
     * taken from the drawable folder
     */
    val imageId: Int){

    /**
     * Amount to order of the product
     * not to insert directly, is updated in the item
     */
    var amount: Int = 0
}