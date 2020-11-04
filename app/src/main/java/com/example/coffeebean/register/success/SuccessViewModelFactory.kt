package com.example.coffeebean.register.success

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class SuccessViewModelFactory(val username: String): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SuccessViewModel::class.java))
            return SuccessViewModel(username) as T

        throw IllegalArgumentException ("NOT THE SAME VIEWMODEL")
    }

}