package com.example.coffeebean.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.example.coffeebean.R
import com.example.coffeebean.database.CoffeeDatabase
import com.example.coffeebean.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeCoffeeViewModel: HomeCoffeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        val application = requireNotNull(this.activity).application

        val coffeeDatabaseDao = CoffeeDatabase.getInstance(application).coffeeDatabaseDao

        val homeCoffeeViewModelFactory = HomeCoffeeViewModelFactory(coffeeDatabaseDao, application)

        homeCoffeeViewModel = ViewModelProvider(this, homeCoffeeViewModelFactory).get(HomeCoffeViewModel::class.java)



        binding.apply {
            accessButton.setOnClickListener {
                val username = usernameEditText.text.toString()
                val password = passwordEditText.text.toString()

                if (username.isNotEmpty()){
                    if (password.isNotEmpty()){
                        homeCoffeeViewModel.user(username, password)
                    }
                    else
                        Snackbar.make(binding.root, "Password non inserita", Snackbar.LENGTH_LONG).show()
                }
                else
                    Snackbar.make(binding.root, "Username non inserito", Snackbar.LENGTH_LONG).show()
            }

            registerButton.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_registerFragment2)
            }
        }

        homeCoffeeViewModel.singIn.observe(viewLifecycleOwner, Observer { singIn ->
            singIn.let {
                if (singIn){
                    navigate()
                    homeCoffeeViewModel.singInFinished()
                }
            }
        })

        homeCoffeeViewModel.showSnackBar.observe(viewLifecycleOwner, Observer { snack ->
            snack.let {
                if (snack){
                    Snackbar.make(binding.root, "Accout Insesistente", Snackbar.LENGTH_LONG).show()
                    homeCoffeeViewModel.showFinished()
                }
            }
        })

        return binding.root
    }

    private fun navigate(){
        val action = HomeFragmentDirections.actionHomeFragmentToIntroFragment(homeCoffeeViewModel.user?.userId!!)
        findNavController().navigate(action)
    }

}