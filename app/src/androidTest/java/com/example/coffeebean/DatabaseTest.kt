package com.example.coffeebean

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.coffeebean.database.CoffeeDatabase
import com.example.coffeebean.database.CoffeeDatabaseDao
import com.example.coffeebean.database.entities.Product
import com.example.coffeebean.database.entities.User
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    private lateinit var coffeeDao: CoffeeDatabaseDao
    private lateinit var db: CoffeeDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(context, CoffeeDatabase::class.java).allowMainThreadQueries().build()

        coffeeDao = db.coffeeDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun testMethods() {
        /**
         * Create two Placeholder Users and insert them in [db] with [coffeeDao]
         */
        val user: User = User(
            //The userId value is just for test
            userId = 1, username = "Hermit", name = "Giovanni", surname = "Scorziello", password = "Arca0912@"
        )
        coffeeDao.insertUser(user)

        val user2: User = User(
            //The userId value is just for test
            userId = 2, username = "craig", name = "Antono", surname = "Ricci", password = "arca0912"
        )
        coffeeDao.insertUser(user2)

        /**
         * The same thing with Product, one for a user and one for another
         */
        val product = Product(
            name = "Caffe", description = "Un caffe normale", amount = 4, price = 0.90, userId =  user.userId, imageId = R.drawable.ic_bellini
        )

        val product2 = Product(
            name = "Caffe", description = "Un caffe normale", amount = 4, price = 0.90, userId =  user2.userId, imageId = R.drawable.ic_bellini
        )

        coffeeDao.insertProducts(listOf(product, product2))

        /**
         * Return a [User] and a [Product] with that userId
         */
        val userResult = coffeeDao.getUser("Hermit", "Arca0912@")
        var productResult = userResult?.userId?.let { id -> coffeeDao.getLastProduct(id) }

        /**
         * Check if they exist, so they are been inserted
         */
        assertThat(userResult?.username, equalTo(user.username))

        assertThat(productResult?.name, equalTo(product.name))

        /**
         * Get a [List] of [User]
         */
        val allUsers = coffeeDao.getAllUsers()

        /**
         * Check if it is empty and throw an [Exception] or print the content
         */
        if (allUsers.isEmpty())
            throw Exception("NO LIST OF USERS")
        else
            Log.d("TEST", allUsers.toString())

        /**
         * Get a [List] of [Product]
         */
        val productsList = userResult?.userId?.let { id -> coffeeDao.getProducts(id) }

        /**
         * Check if it is empty and throw an [Exception] or print the content
         */
        if (productsList!!.isEmpty())
            throw Exception("NO LIST OF PRODUCTS")
        else
            Log.d("TEST", productsList.toString())

        /**
         * Test the [coffeeDao] clear method
         */
        userResult.userId.let { id -> coffeeDao.clearProducts(id) }

        /**
         * Check if it actually cleared the [db]:
         * - Get the list of products for a certain id
         * - CHeck if it is not null
         */
        productResult = userResult.userId.let { id -> coffeeDao.getLastProduct(id) }

        if (productResult != null)
            throw Exception("NO CANCELLATION EXECUTED")

    }
}