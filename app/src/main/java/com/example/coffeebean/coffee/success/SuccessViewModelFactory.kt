package com.example.coffeebean.coffee.success

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coffeebean.database.CoffeeDatabaseDao
import java.lang.IllegalArgumentException

class SuccessViewModelFactory(private val coffeeDatabaseDao: CoffeeDatabaseDao, val application: Application, val userId: Long, val productsNumber: Int): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SuccessViewModel::class.java))
            return SuccessViewModel(coffeeDatabaseDao, productsNumber, userId, application) as T

        throw IllegalArgumentException ("NOT THE SAME VIEWMODEL")
    }
}