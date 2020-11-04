package com.example.coffeebean.coffee

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import com.example.coffeebean.R
import com.example.coffeebean.coffee.list.ProductsAdapter
import com.example.coffeebean.database.CoffeeDatabase
import com.example.coffeebean.databinding.FragmentCoffeeBinding
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 */
class CoffeeFragment : Fragment() {

    private lateinit var binding: FragmentCoffeeBinding
    private lateinit var coffeeViewModel: CoffeeViewModel
    private lateinit var adapter: ProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val args: CoffeeFragmentArgs by navArgs()

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_coffee, container, false)

        val application = requireNotNull(activity).application

        val databaseDao = CoffeeDatabase.getInstance(application).coffeeDatabaseDao

        val factory = CoffeeViewModelFactory(databaseDao, args.userId, application)

        coffeeViewModel = ViewModelProvider(this, factory).get(CoffeeViewModel::class.java)

        binding.lifecycleOwner = this

        adapter = ProductsAdapter()
        adapter.submitList(coffeeViewModel.productsList)

        binding.apply {
            recyclerView.adapter = adapter
            erogateButton.setOnClickListener {
                coffeeViewModel.populateListForDB(adapter.currentList)
            }
        }

        coffeeViewModel.toSuccessFragment.observe( viewLifecycleOwner, { go ->
            go.let {
                if (go == true){
                    navigate(coffeeViewModel.userId, coffeeViewModel.productsSize)
                    coffeeViewModel.stopNavigation()
                }
            }
        })

        coffeeViewModel.showSnackbar.observe( viewLifecycleOwner, { show ->
            show.let {
                if (show == true){
                    Snackbar.make(binding.root, "Niente da Ordinare", Snackbar.LENGTH_LONG).show()
                    coffeeViewModel.stopShowing()
                }
            }
        })

        return binding.root
    }

    private fun navigate(userId: Long, productNumbers: Int){
        val action = CoffeeFragmentDirections.actionCoffeeFragment2ToSuccessCoffeeFragment(userId, productNumbers)
        coffeeViewModel.onCleared()
        findNavController().navigate(action)
    }

}