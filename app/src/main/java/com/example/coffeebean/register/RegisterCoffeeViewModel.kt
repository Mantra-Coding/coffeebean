package com.example.coffeebean.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.coffeebean.R
import com.example.coffeebean.database.CoffeeDatabaseDao
import com.example.coffeebean.database.entities.User
import kotlinx.coroutines.*

/**
 * Constant values for the validation user field
 */
const val GOOD_USERNAME: String = "Perfetto!"
const val TOO_SHORT_USERNAME: String = "Troppo Corto"
const val ALREADY_EXIST_USERNAME: String = "Già Esistente"

/**
 * Constant values for password validation
 */
const val NOT_VALID_PASSWORD: String = "§  \"  \\  /  *  +  ,  :  ;  non sono caratteri validi!"
const val WEAK_PASSWORD: String = "Protezione Debole"
const val MEDIUM_PASSWORD: String = "Protezione Media"
const val STRONG_PASSWORD: String = "Protezione Forte"

class RegisterCoffeeViewModel(private val coffeeDatabaseDao: CoffeeDatabaseDao, application: Application): AndroidViewModel(application){

    /**
     * [Job] for the UI Scope Coroutine
     */
    private val registerViewModelJob = Job()

    /**
     * [uiScope] for the main thread actions
     */
    private val uiScope = CoroutineScope(Dispatchers.Main + registerViewModelJob)

    /**
     * [MutableLiveData] [String] value [_username] for the username filed changes
     * For internal use
     */
    private var _username = MutableLiveData<String>()

    /**
     * [_username] external use getter variable (Encapsulation)
     */
    val username: LiveData<String>
        get() = _username

    /**
     * [MutableLiveData] [String] value [_password] for the password filed changes
     * For internal use
     */
    private var _password = MutableLiveData<String>()

    /**
     * [_password] external use getter variable (Encapsulation)
     */
    val password: LiveData<String>
            get() = _password

    /**
     * [MutableLiveData] [String] value [_usernameLabel] for the username label changes
     * For internal use
     */
    private var _usernameLabel = MutableLiveData<String>()

    /**
     * [_usernameLabel] external use getter variable (Encapsulation)
     */
    val usernameLabel: LiveData<String>
        get() = _usernameLabel

    /**
     * [MutableLiveData] [String] value [_passwordLabel] for the password label changes
     * For internal use
     */
    private var _passwordLabel =  MutableLiveData<String>()

    /**
     * [_passwordLabel] external use getter variable (Encapsulation)
     */
    val passwordLabel: LiveData<String>
        get() = _passwordLabel

    /**
     * [List] of all the users in the database
     */
    private lateinit var allUsers: List<User>

    /**
     * Event variable for switch fragment
     */
    private var _insert = MutableLiveData<Boolean>()

    /**
     * [_insert] external use variable (Encapsulation)
     */
    val insert: LiveData<Boolean>
        get() = _insert

    /**
     * reset the [_insert] value
     */
    fun inserted(){
        _insert.value = false
    }

    private var _showSnackBar = MutableLiveData<Boolean>()
    val showSnackBar: LiveData<Boolean>
        get() = _showSnackBar

    fun showed(){
        _showSnackBar.value = false
    }

    init {
        users()
    }

    /**
     * [checkUser] check if the user to insert is valid
     * it use the controls already made on the fields in [usernameLabelText] and [passwordLabelText]
     * in this function we use the [usernameLabel] and [passwordLabel] text for know if the user is valid
     */
    fun checkUser(name: String, surname: String){
        uiScope.launch {
            if (username.value != null && usernameLabel.value == GOOD_USERNAME && passwordLabel.value != null && passwordLabel.value == STRONG_PASSWORD){
                insertUser(name, surname)
                _insert.value = true
            }else{
                _showSnackBar.value = true
            }
        }
    }

    /**
     * [insertUser] Insert the new [User] in the DB
     *
     * @param name the name of the user
     * @param surname the surname of the user
     */
    private fun insertUser(name: String, surname: String){
        viewModelScope.launch {
            val user = User(username = username.value!!, name = name, surname = surname, password = password.value!!)
            insert(user)
        }
    }

    /**
     * IO Thread function for insert the [User] in the database
     * with the [CoffeeDatabaseDao] method
     *
     * @param user the new user to insert
     */
    private suspend fun insert(user: User){
        withContext(Dispatchers.IO){
            coffeeDatabaseDao.insertUser(user)
        }
    }

    override fun onCleared() {
        super.onCleared()
        registerViewModelJob.cancel()
    }


    /**
     * get a list of the [User] already existing inside the database,
     * so we can check if the user is inserting an already existing username
     */
    private fun users(){
        viewModelScope.launch {
            allUsers = all()
        }
    }

    /**
     * IO Thread function for get the list of user in the database
     * with the [CoffeeDatabaseDao] method
     */
    private suspend fun all(): List<User>{
        return withContext(Dispatchers.IO){
            coffeeDatabaseDao.getAllUsers()
        }
    }

    /**
     * change the [_username] value at the username EditText content change
     */
    fun usernameEditTextOnChange(username: String){
        _username.value = username
    }

    /**
     * change the [_password] value at the password EditText content change
     */
    fun passwordEditTextOnChange(password: String){
        _password.value = password
    }

    /**
     * [usernameLabelText] set the username label text for let the user know if his username is valid or not
     */
    fun usernameLabelText(){
        var flag = 3

        if (_username.value?.length!! < 6) {
            flag = 1
        }
        else {

            if (allUsers.isNotEmpty()) {
                val pojo = allUsers.find {
                    it.username == _username.value
                }
                flag = if (pojo != null) 2 else 3
            }

        }

        _usernameLabel.value = when(flag){
            1 -> TOO_SHORT_USERNAME
            2 -> ALREADY_EXIST_USERNAME
            3 -> GOOD_USERNAME
            else -> ""
        }
    }

    /**
     * [checkIfIsSecure] is a method for the validation of the [User] password field
     * it check the strong of the password, for be a secure password it needs:
     * - Special Characters
     * - Characters in caps
     * - Numbers
     * it check also if the password contains also not valid characters
     *
     * @param password the password to check
     *
     */
    private fun checkIfIsSecure(password: String?): Int{

        val numbers = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0")
        val notValid = listOf("§", "\"", "\\", "/", "*", "+", ",", ":", ";")
        val specialCharacters = listOf("#", "@", "!", "|", "£", "$", "%", "&", "(", ")", "{", "}", "[", "]", ".", "-", "_", "?", "^", "<", ">")
        val capsCharacter = listOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "Z", "W", "K", "J", "X", "Y")

        if (password == null)
            return 0

        val matchNotValid = notValid.filter { invalid ->
            invalid in password
        }

        if (matchNotValid.isNotEmpty())
            return -1

        if (password.length <= 4)
            return 0

        val matchNumbers = numbers.filter { num ->
            num in password
        }

        val matchSpecialCharacters = specialCharacters.filter { special ->
            special in password
        }

        val matchCapsCharacters = capsCharacter.filter { caps ->
            caps in capsCharacter
        }


        if (password.length in 5..7){
            return if (matchNumbers.isNotEmpty() || matchSpecialCharacters.isNotEmpty() || matchCapsCharacters.isNotEmpty()){
                1
            } else{
                0
            }
        }

        if (password.length >= 8){
            return if (matchNumbers.isNotEmpty() && matchSpecialCharacters.isNotEmpty() && matchCapsCharacters.isNotEmpty())
                2
            else{
                if ((matchNumbers.isNotEmpty() || matchSpecialCharacters.isNotEmpty() || matchCapsCharacters.isNotEmpty())){
                    1
                } else
                    0
            }
        }

        return 1
    }


    fun passwordLabelText(){

        when(checkIfIsSecure(_password.value)){
            -1 -> _passwordLabel.value = NOT_VALID_PASSWORD
            0 -> _passwordLabel.value = WEAK_PASSWORD
            1 -> _passwordLabel.value = MEDIUM_PASSWORD
            2 -> _passwordLabel.value = STRONG_PASSWORD
        }

    }
}