package com.example.coffeebean.coffee.success

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.coffeebean.database.CoffeeDatabaseDao
import com.example.coffeebean.database.entities.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SuccessViewModel(
    val coffeeDatabaseDao: CoffeeDatabaseDao,
    val productNumber: Int,
    val userId: Long,
    application: Application
) :
    AndroidViewModel(application) {

    private var _orderedList = MutableLiveData<List<Product>>()
    val orderedList: LiveData<List<Product>>
        get() = _orderedList

    init {
        getList()
    }

    private fun getList() {
        viewModelScope.launch {
            _orderedList.value = orderedProducts()
        }
    }

    fun exeded() {
        viewModelScope.launch {
            val list = getAllProducts()
            if (list.size > 30) {
                delateEx(list.subList(30, list.lastIndex))
                Log.i("COFFEE BACKGROUND", "MAKE")
            }
        }
    }

    private suspend fun getAllProducts(): List<Product> {
        return withContext(Dispatchers.IO) {
            coffeeDatabaseDao.getProducts(userId)
        }
    }


    private suspend fun delateEx(products: List<Product>) {
        withContext(Dispatchers.IO) {
            coffeeDatabaseDao.delateExededProducts(products)
        }
    }

    private suspend fun orderedProducts(): List<Product> {
        return withContext(Dispatchers.IO) {
            coffeeDatabaseDao.getLastOrder(userId, productNumber)
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}