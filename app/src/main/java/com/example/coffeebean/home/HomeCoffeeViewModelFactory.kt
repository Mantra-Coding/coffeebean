package com.example.coffeebean.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coffeebean.database.CoffeeDatabaseDao
import java.lang.IllegalArgumentException

class HomeCoffeeViewModelFactory(val coffeeDatabaseDao: CoffeeDatabaseDao, val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeCoffeViewModel::class.java)){
            return HomeCoffeViewModel(coffeeDatabaseDao, application) as T
        }
        throw IllegalArgumentException ("NOT THE SAME VIEWMODEL")
    }
}