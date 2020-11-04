package com.example.coffeebean.coffee

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.coffeebean.ProductPlaceholder
import com.example.coffeebean.R
import com.example.coffeebean.database.CoffeeDatabaseDao
import com.example.coffeebean.database.entities.Product
import com.example.coffeebean.database.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CoffeeViewModel(val coffeeDatabaseDao: CoffeeDatabaseDao, val userId: Long, application: Application): AndroidViewModel(application){

    private var _productsList = mutableListOf<ProductPlaceholder>()
    val productsList: MutableList<ProductPlaceholder>
        get() = _productsList

    private var _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private var _toSuccessFragment = MutableLiveData<Boolean>()
    val toSuccessFragment: LiveData<Boolean>
        get() = _toSuccessFragment

    fun stopNavigation(){
        _toSuccessFragment.value = false
    }

    private var _showSnackbar = MutableLiveData<Boolean>()
    val showSnackbar: LiveData<Boolean>
        get() = _showSnackbar

    fun stopShowing(){
        _showSnackbar.value = false
    }

    private var products = mutableListOf<Product>()
    val productsSize: Int
        get() = products.size


    private fun populateList(){
        _productsList.add(ProductPlaceholder("Moscow Mule", "• Vodka\n• Ginger Beer\n• Lime Juice", 15.0, R.drawable.ic_moscow_mule))
        _productsList.add(ProductPlaceholder("Aperol Spritz", "• Prosecco\n• Aperol\n• Club Soda", 10.0, R.drawable.ic_spritz))
        _productsList.add(ProductPlaceholder("Margarita", "• Silver Tequila\n• Cointreau\n• Lime Juice", 10.0, R.drawable.ic_margarita))
        _productsList.add(ProductPlaceholder("Espresso Martini", "• Vodka\n• Coffee Liquor\n• Espresso\n• Sciroppo Semplice", 20.0, R.drawable.ic_espresso_maritni))
        _productsList.add(ProductPlaceholder("Dry Martini", "• Vodka o Gin\n• Dry Veromuth", 15.0, R.drawable.ic_dry_martini))
        _productsList.add(ProductPlaceholder("Manhattan", "• Whiskey\n• Caprano Antica(Vermouth Dolce)\n• Bitter", 15.0, R.drawable.ic_manhattan))
        _productsList.add(ProductPlaceholder("Daiquiri", "• Rum Bianco\n• Zucchero\n• Lemon Juice", 10.0, R.drawable.ic_daiquiri))
        _productsList.add(ProductPlaceholder("Negroni", "• London Dry Gin\n• Campari\n• Vermouth Rosso", 10.0, R.drawable.ic_negroni))
        _productsList.add(ProductPlaceholder("Birra", "", 8.0, R.drawable.ic_birra))
        _productsList.add(ProductPlaceholder("Succhi", "", 8.0, R.drawable.ic_succo))
        _productsList.add(ProductPlaceholder("Bellini", "• Prosecco\n• Polpa di Pesca", 15.0, R.drawable.ic_bellini))
        _productsList.add(ProductPlaceholder("Cuba Libre", "• Rum Bianco\n• Coca Cola\n• Lime", 10.0, R.drawable.ic_cuba_libre))

        _productsList.sortBy {
            it.name
        }

    }

    private fun getUser(){
        viewModelScope.launch {
            _user.value = get(userId)
        }
    }

    fun populateListForDB(productsFromList: List<ProductPlaceholder>){
        productsFromList.forEach { product ->
            if (product.amount > 0) {
                products.add(
                    Product(
                        name = product.name,
                        description = product.description,
                        amount = product.amount,
                        imageId = product.imageId,
                        userId = userId,
                        price = product.price
                    )
                )
                Log.i("CoffeeViewModel", product.name)
            }
        }

        if (products.isNotEmpty()) {
            insertProducts()
            _toSuccessFragment.value = true
        }
        else
            _showSnackbar.value = true

    }

    fun clearProducts(){
        products.clear()
    }

    private suspend fun get(userId: Long): User?{
        return withContext(Dispatchers.IO){
            coffeeDatabaseDao.getUserById(userId)
        }
    }

    init {
        populateList()
        getUser()
    }

    private fun insertProducts(){
        viewModelScope.launch {
            insert(products)
        }
    }

    private suspend fun insert(productsList: List<Product>){
        withContext(Dispatchers.IO){
            coffeeDatabaseDao.insertProducts(productsList)
        }
    }

    public override fun onCleared() {
        super.onCleared()
    }

}