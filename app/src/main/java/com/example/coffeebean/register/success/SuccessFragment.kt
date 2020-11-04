package com.example.coffeebean.register.success

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.coffeebean.R
import com.example.coffeebean.databinding.FragmentSuccessBinding


/**
 * A simple [Fragment] subclass.
 */
class SuccessFragment : Fragment() {

    private lateinit var binding: FragmentSuccessBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_success, container, false)

        val args =
            SuccessFragmentArgs.fromBundle(
                requireArguments()
            )

        binding.lifecycleOwner = this

        val successViewModelFactory = SuccessViewModelFactory(args.username)

        val successViewModel = ViewModelProvider(this, successViewModelFactory).get(SuccessViewModel::class.java)

        binding.successViewModel = successViewModel

        return binding.root
    }

}
