package com.example.coffeebean.intro

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.coffeebean.database.CoffeeDatabaseDao
import com.example.coffeebean.database.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IntroViewModel(val userId: Long, val coffeeDatabaseDao: CoffeeDatabaseDao, application: Application): AndroidViewModel(application){

    private var _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    init {
        getUser()
    }

    private fun getUser(){
        viewModelScope.launch {
            _user.value = getUserById()
        }
    }

    private suspend fun getUserById(): User?{
        return withContext(Dispatchers.IO){
            coffeeDatabaseDao.getUserById(userId)
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}