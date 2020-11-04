package com.example.coffeebean.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.coffeebean.R
import com.example.coffeebean.database.CoffeeDatabase
import com.example.coffeebean.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 */

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    lateinit var coffeeViewModel: RegisterCoffeeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)

        val app = requireNotNull(this.activity).application

        val coffeeDatabaseDao = CoffeeDatabase.getInstance(app).coffeeDatabaseDao

        val coffeeViewModelFactory = RegisterCoffeeViewModelFactory(coffeeDatabaseDao, app)

        coffeeViewModel =
            ViewModelProvider(this, coffeeViewModelFactory).get(RegisterCoffeeViewModel::class.java)

        binding.lifecycleOwner = this

        binding.registerViewModel = coffeeViewModel

        coffeeViewModel.username.observe(viewLifecycleOwner, {
            coffeeViewModel.usernameLabelText()
        })

        coffeeViewModel.password.observe(viewLifecycleOwner, {
            coffeeViewModel.passwordLabelText()
        })

        coffeeViewModel.insert.observe(viewLifecycleOwner, { nav ->
            nav.let {
                if (nav == true) {
                    navigate()
                    coffeeViewModel.inserted()
                }
            }
        })

        coffeeViewModel.showSnackBar.observe(viewLifecycleOwner, { show ->
            show.let {
                if (show == true) {
                    Snackbar.make(binding.root, "Impossibile Effettuare la registrazione",Snackbar.LENGTH_LONG).show()
                    coffeeViewModel.showed()
                }
            }
        })

        binding.apply {
            usernameEditText.addTextChangedListener(
                UsernameTextWatcher(
                    coffeeViewModel,
                    "username"
                )
            )
            passwordEditText.addTextChangedListener(
                UsernameTextWatcher(
                    coffeeViewModel,
                    "password"
                )
            )

            registerButton.setOnClickListener {
                val name = nameEditText.text.toString()
                val surname = surnameEditText.text.toString()
                if (name.isNotEmpty()) {
                    if (surname.isNotEmpty()) {
                        coffeeViewModel.checkUser(name, surname)
                    }else{
                        Snackbar.make(binding.root, "Campo cognome obbligatorio!",Snackbar.LENGTH_LONG).show()
                    }
                }else{
                    Snackbar.make(binding.root, "Campo nome obbligatorio!",Snackbar.LENGTH_LONG).show()
                }
            }
        }

        return binding.root
    }

    private fun navigate() {
        val action =
            RegisterFragmentDirections.actionRegisterFragment2ToSuccessFragment(coffeeViewModel.username.value.toString())
        this.findNavController().navigate(action)
    }


    /**
     * Custom [TextWatcher] for username or password EditText
     *
     * @param coffeeViewModel for the observable variable of the username or password
     * @param fieldToChange choose if the text to update is of the username or the password
     */
    private class UsernameTextWatcher(
        private val coffeeViewModel: RegisterCoffeeViewModel,
        private val fieldToChange: String
    ) : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            when (fieldToChange) {
                "username" -> coffeeViewModel.usernameEditTextOnChange(s.toString())
                "password" -> coffeeViewModel.passwordEditTextOnChange(s.toString())
            }
        }

        override fun afterTextChanged(s: Editable?) {

        }
    }

}
