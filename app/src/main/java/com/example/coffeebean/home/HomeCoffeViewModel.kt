package com.example.coffeebean.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.coffeebean.database.CoffeeDatabaseDao
import com.example.coffeebean.database.entities.User
import kotlinx.coroutines.*

class HomeCoffeViewModel (private val coffeeDatabaseDao: CoffeeDatabaseDao, application: Application): AndroidViewModel(application){

    private val homeCoffeeViewModelJob = Job()

    private var _user: User? = null
    val user: User?
        get() = _user

    private var _singIn = MutableLiveData<Boolean>()
    val singIn: LiveData<Boolean>
        get() = _singIn

    fun singInFinished(){
        _singIn.value = false
    }

    private var _showSnackBar = MutableLiveData<Boolean>()
    val showSnackBar: LiveData<Boolean>
        get() = _showSnackBar

    fun showFinished() {
        _showSnackBar.value = false
    }

    fun user(username: String, password: String){
        viewModelScope.launch {
            _user = getUser(username, password)
            if (_user != null)
                _singIn.value = true
            else
                _showSnackBar.value = true
        }
    }

    private suspend fun getUser(username: String, password: String): User?{
        return withContext(Dispatchers.IO){
            coffeeDatabaseDao.getUser(username, password)
        }
    }

    override fun onCleared() {
        super.onCleared()
        homeCoffeeViewModelJob.cancel()
    }
}