package com.example.coffeebean.orders

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coffeebean.database.CoffeeDatabaseDao
import java.lang.IllegalArgumentException

class OrdersViewModelFactory(val userId: Long, val coffeeDatabaseDao: CoffeeDatabaseDao, val application: Application)
    :ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrdersViewModel::class.java))
            return OrdersViewModel(userId, coffeeDatabaseDao, application) as T
        throw IllegalArgumentException("NOT THE SAME VIEWMODEL")
    }

}