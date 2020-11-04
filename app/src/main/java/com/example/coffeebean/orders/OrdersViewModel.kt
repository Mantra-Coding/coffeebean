   package com.example.coffeebean.orders

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.coffeebean.database.CoffeeDatabaseDao
import com.example.coffeebean.database.entities.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val INITIAL_VALUE = 0
const val IS_FREE = 1
const val CONTAINS_SOMETHING = 2

class OrdersViewModel(
    private val userId: Long,
    private val coffeeDatabaseDao: CoffeeDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var _ordersList = MutableLiveData<List<Product>>()
    val ordersList: LiveData<List<Product>>
        get() = _ordersList

    init {
        initializeList()
    }

    private var _showRecycler = MutableLiveData<Int>()
    val showRecycler: LiveData<Int>
        get() = _showRecycler

    fun reset(){
        _showRecycler.value = INITIAL_VALUE
    }

    private fun initializeList() {
        viewModelScope.launch {
            _ordersList.value = products()
            _showRecycler.value = if (_ordersList.value.isNullOrEmpty()) IS_FREE else CONTAINS_SOMETHING
        }
    }

    private suspend fun products(): List<Product> {
        return withContext(Dispatchers.IO) {
            coffeeDatabaseDao.getProducts(userId)
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}