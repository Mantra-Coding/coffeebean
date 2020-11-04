package com.example.coffeebean.intro

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coffeebean.database.CoffeeDatabaseDao
import java.lang.IllegalArgumentException

class IntroViewModelFactory (val userId: Long, val coffeeDatabaseDao: CoffeeDatabaseDao, val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IntroViewModel::class.java))
            return IntroViewModel(userId, coffeeDatabaseDao, application) as T

        throw IllegalArgumentException("NOT THE SAME VIEWMODEL")
    }
}