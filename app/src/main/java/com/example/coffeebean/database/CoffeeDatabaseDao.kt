package com.example.coffeebean.database

import androidx.room.*
import com.example.coffeebean.database.entities.Product
import com.example.coffeebean.database.entities.User

@Dao
interface CoffeeDatabaseDao {

    /**
     * Insert a [User] Entity
     *
     * @param user new User to insert
     *
     */
    @Insert(entity = User::class)
    fun insertUser(user: User)

    /**
     * Insert one or more [Product] Entity
     *
     * @param product new Product to insert
     *
     */
    @Insert(entity = Product::class)
    fun insertProducts(product: List<Product>)

    /**
     * Update the [User] with a new password, username, name or surname
     */
    @Update(entity = User::class)
    fun updateUser(user: User)

    /**
     *
     */
    @Update(entity = Product::class)
    fun updateProducts(vararg product: Product)

    /**
     *
     */
    @Delete(entity = Product::class)
    fun delateExededProducts(product: List<Product>)

    /**
     * Get an [User] account with username and password (a normal login)
     *
     * @param username the [User] username
     * @param password the [User] password
     *
     */
    @Query("SELECT * FROM table_user WHERE username == :username AND password == :password")
    fun getUser(username: String, password: String): User?


    /**
     *
     */
    @Query("SELECT * FROM table_user WHERE userId == :id")
    fun getUserById(id: Long): User?

    /**
     * Get the [List] of all the [User] in the Database
     */
    @Query("SELECT * FROM table_user ORDER BY userId DESC")
    fun getAllUsers(): List<User>

    /**
     * Get a [List] of [Product] of a certain [User]
     *
     * @param userId the id of the [User]
     *
     */
    @Query("SELECT * FROM table_product WHERE userId == :userId ORDER BY day_order DESC")
    fun getProducts(userId: Long): List<Product>

    /**
     * Get a [List] of a type of [Product] with a certain name of a certain [User]
     *
     * @param userId the id of the [User]
     * @param productName the name of the [Product]
     *
     */
    @Query("SELECT * FROM table_product WHERE userId == :userId AND product_name == :productName")
    fun getSelectedProducts(userId: Long, productName: String): List<Product>

    /**
     *
     */
    @Query("SELECT * FROM table_product WHERE userId == :userId ORDER BY productId DESC LIMIT :numberOfProducts")
    fun getLastOrder(userId: Long, numberOfProducts: Int): List<Product>

    /**
     * Get the last [Product] ordered by an [User]
     *
     * @param userId the id of the [User]
     *
     */
    @Query("SELECT * FROM table_product WHERE userId == :userId ORDER BY userId DESC LIMIT 1")
    fun getLastProduct(userId: Long): Product?

    /**
     * Delete all the [Product] of a certain [User]
     *
     * @param userId the id of the [User]
     *
     */
    @Query("DELETE FROM table_product WHERE userId == :userId")
    fun clearProducts(userId: Long)
}