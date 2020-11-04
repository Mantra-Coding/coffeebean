package com.example.coffeebean.coffee

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coffeebean.database.CoffeeDatabaseDao
import java.lang.IllegalArgumentException

class CoffeeViewModelFactory(val coffeeDatabaseDao: CoffeeDatabaseDao, val userId: Long, val application: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CoffeeViewModel::class.java))
            return CoffeeViewModel(coffeeDatabaseDao, userId, application) as T

        throw IllegalArgumentException("NOT THE SAME VIEWMODEL")
    }
}