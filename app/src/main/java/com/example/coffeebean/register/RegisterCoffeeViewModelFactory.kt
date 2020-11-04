package com.example.coffeebean.register

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coffeebean.database.CoffeeDatabaseDao
import java.lang.IllegalArgumentException

class RegisterCoffeeViewModelFactory(private val coffeeDatabaseDao: CoffeeDatabaseDao,val application: Application):
    ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterCoffeeViewModel::class.java)){
            return RegisterCoffeeViewModel(coffeeDatabaseDao, application) as T
        }
        throw IllegalArgumentException("NOT THE SAME CLASS")
    }
}