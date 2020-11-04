package com.example.coffeebean.register.success

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SuccessViewModel(val username: String): ViewModel(){

    private var _usernameLive = MutableLiveData<String>()
    val usernameLive: LiveData<String>
        get() = _usernameLive

    init {
        _usernameLive.value = username
    }

}