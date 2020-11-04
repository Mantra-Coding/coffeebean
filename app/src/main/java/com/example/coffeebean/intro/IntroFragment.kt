package com.example.coffeebean.intro

import android.database.DatabaseUtils
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.coffeebean.R
import com.example.coffeebean.coffee.CoffeeFragmentDirections
import com.example.coffeebean.database.CoffeeDatabase
import com.example.coffeebean.databinding.FragmentIntroBinding

/**
 * A simple [Fragment] subclass.
 */
class IntroFragment : Fragment() {

    private lateinit var binding: FragmentIntroBinding
    private lateinit var introViewModel: IntroViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_intro, container, false)

        val args: IntroFragmentArgs by navArgs()

        setHasOptionsMenu(true)

        val application = requireNotNull(activity).application

        val coffeeDatabaseDao = CoffeeDatabase.getInstance(application).coffeeDatabaseDao

        val factory = IntroViewModelFactory(args.userId, coffeeDatabaseDao, application)

        introViewModel = ViewModelProvider(this, factory).get(IntroViewModel::class.java)

        binding.lifecycleOwner = this

        binding.user = introViewModel

        binding.apply {
            makeOrderButton.setOnClickListener {
                findNavController().navigate(IntroFragmentDirections.actionIntroFragmentToCoffeeFragment2(args.userId))
            }
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.coffee_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.ordersFragment -> findNavController().navigate(IntroFragmentDirections.actionIntroFragmentToOrdersFragment(introViewModel.userId))
            R.id.mantra_item -> findNavController().navigate(IntroFragmentDirections.actionIntroFragmentToMantraFragment())
        }

        return true
    }

}
